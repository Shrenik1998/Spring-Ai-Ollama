package com.ollama.lamma.tools;

import com.ollama.lamma.entity.User;
import com.ollama.lamma.model.CreateUserRequest;
import com.ollama.lamma.model.UserPrompt;
import com.ollama.lamma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class UserServiceTool {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTool.class);
    private final UserService userService;

    public UserServiceTool(UserService userService) {
        this.userService = userService;
    }

    @Tool(name = "createUser", description = "Call this function to CREATE a new user in the database. Requires name and email.")
    public String createUser(
            @ToolParam(description = "The full name of the user") String name,
            @ToolParam(description = "The email address of the user") String email,
            ToolContext context
    ){
        String username = (String) context.getContext().get("username");

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        User saved = userService.createUser(user);

        LOGGER.info("User created id={}, requestedBy={}", saved.getId(), username);

        return "User created successfully with id " + saved.getId();
    }

    @Tool(name = "getUserByEmail", description = "Call this function to get user details using email")
    public User getUserByEmail(@ToolParam(description = "the email of the user") String email){
        User user = userService.getUserByEmail(email);

        return user;
    }
}
