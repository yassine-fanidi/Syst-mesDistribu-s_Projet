package org.fanidiyassine.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class EbankAIAgent {
    private ChatClient chatClient;
    public EbankAIAgent(ChatClient.Builder chatClient, ChatMemory chatMemory, ToolCallbackProvider tools) {
        this.chatClient = chatClient
                .defaultSystem("""
                        Vous êtes un assistant qui se charge de répondre aux questionst
                        de l'utilisateur à propos des clients et des comptes bancaires, en fonction du contexte fourni.
                        Si aucun contexet n'est fourni, répond avec Malheuresement JE NE SAIS PAS
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools((Object[]) tools.getToolCallbacks())
                .build();
    }
    public String chat(String query){
        return chatClient.prompt(query)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, "default-session"))
                .call().content();
    }
}
