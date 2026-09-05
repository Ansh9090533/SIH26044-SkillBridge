package com.skillbridge.skillbridge_backend.dto;

public class LoginResponse {

    private Integer userId;
    private String email;
    private String role;
    private String message;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(Integer userId, String email, String role,
                         String message, String token) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.message = message;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}