package com.skillbridge.skillbridge_backend.controller;

import com.skillbridge.skillbridge_backend.dto.LoginRequest;
import com.skillbridge.skillbridge_backend.dto.LoginResponse;
import com.skillbridge.skillbridge_backend.dto.RegisterRequest;
import com.skillbridge.skillbridge_backend.dto.RegisterResponse;
import com.skillbridge.skillbridge_backend.entity.User;
import com.skillbridge.skillbridge_backend.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        RegisterResponse response = new RegisterResponse(
                user.getUserId(),
                user.getEmail(),
                user.getRole(),
                "Registration successful"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        User user = authService.getUserByEmail(request.getEmail());

        LoginResponse response = new LoginResponse(
                user.getUserId(),
                user.getEmail(),
                user.getRole(),
                "Login successful",
                token
        );

        return ResponseEntity.ok(response);
    }
}