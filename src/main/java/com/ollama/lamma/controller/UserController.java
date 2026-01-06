package com.ollama.lamma.controller;

import com.ollama.lamma.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import com.ollama.lamma.entity.User;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/createUser")
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/getAllUser")
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
