package com.example.GestionTorneos.dto;

public class UserResponseDTO {
    private String token;

    public UserResponseDTO(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}