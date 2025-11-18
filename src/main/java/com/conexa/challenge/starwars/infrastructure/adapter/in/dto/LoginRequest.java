package com.conexa.challenge.starwars.infrastructure.adapter.in.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
