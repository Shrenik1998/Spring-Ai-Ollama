package com.ollama.lamma.controller;

import com.ollama.lamma.entity.User;
import com.ollama.lamma.tools.UserServiceTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("api/user")
public class UserPrompController {

    private final ChatClient chatClient;
    private final UserServiceTool userServiceTool;

    public UserPrompController(@Qualifier("userChatClient") ChatClient chatClient,
                              UserServiceTool userServiceTool) {
        this.chatClient = chatClient;
        this.userServiceTool = userServiceTool;
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> userAssistant(@RequestHeader("username") String username,
                                                @RequestParam("prompt") String prompt) {
        String answer = chatClient.prompt()
        .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(prompt)
                .tools(userServiceTool)
                .toolContext(Map.of("username",username))
                .call().content();
        return ResponseEntity.ok(answer);
    }
}
