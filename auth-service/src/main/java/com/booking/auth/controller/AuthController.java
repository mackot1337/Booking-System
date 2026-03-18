package com.booking.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.auth.dto.LoginRequest;
import com.booking.auth.dto.LoginResponse;
import com.booking.auth.dto.RegisterRequest;
import com.booking.auth.dto.TokenDto;
import com.booking.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        LoginResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody TokenDto token) {
        Boolean response = authService.validateToken(token.getToken());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/extract-email")
    public ResponseEntity<String> extractEmail(@RequestBody TokenDto token) {
        String response = authService.extractEmail(token.getToken());
        return ResponseEntity.ok(response);
    }

}
