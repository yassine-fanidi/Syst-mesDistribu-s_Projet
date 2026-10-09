package org.fanidiyassine.ebankbot.controllers;

import org.fanidiyassine.ebankbot.agents.EbankAIAgent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
public class EbankChatbotController {
    private EbankAIAgent  ebankAIAgent;
    public EbankChatbotController(EbankAIAgent  ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }
    @GetMapping("/chat")
    public String chat(@RequestParam(name = "query", defaultValue = "Bonjour") String query){
        return ebankAIAgent.chat(query);
    }
}
