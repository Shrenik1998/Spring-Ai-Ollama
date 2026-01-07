package com.ollama.lamma.model;

public record CreateUserRequest(
        String name,
        String email
) {}
