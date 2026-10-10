package org.fanidiyassine.ebankbot.discord;

import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.fanidiyassine.ebankbot.agents.EbankAIAgent;

import java.util.concurrent.CompletableFuture;

@DiscordController
public class DiscordBot {
    private final EbankAIAgent ebankAIAgent;

    public DiscordBot(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String query = event.getMessage().getContentRaw();

        // 1. On libère immédiatement le thread Discord en exécutant l'IA de manière asynchrone
        CompletableFuture.runAsync(() -> {
            try {
                // L'appel lourd LLM + MCP s'exécute sur un thread séparé
                String response = ebankAIAgent.chat(query);

                // 2. On envoie la réponse en gérant le découpage des 2000 caractères
                sendSplitMessage(event, response);

            } catch (Exception e) {
                System.err.println("Erreur lors de l'exécution de l'agent: " + e.getMessage());
                event.getChannel().sendMessage("⚠️ Le traitement a pris trop de temps ou une erreur MCP est survenue. Veuillez réessayer.").queue();
            }
        });
    }

    private void sendSplitMessage(MessageReceivedEvent event, String text) {
        if (text.length() <= 2000) {
            event.getChannel().sendMessage(text).queue();
            return;
        }

        int index = 0;
        while (index < text.length()) {
            int endIndex = Math.min(index + 2000, text.length());
            String subText = text.substring(index, endIndex);
            event.getChannel().sendMessage(subText).queue();
            index = endIndex;
        }
    }
}
