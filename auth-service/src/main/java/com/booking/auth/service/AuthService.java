package com.booking.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking.auth.dto.LoginRequest;
import com.booking.auth.dto.LoginResponse;
import com.booking.auth.dto.RegisterRequest;
import com.booking.auth.entity.User;
import com.booking.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public LoginResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(User.Role.valueOf(request.getRole()));
        user.setActive(true);

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getRole().toString());

        return new LoginResponse(
            token,
            savedUser.getEmail(),
            savedUser.getFullName(),
            savedUser.getRole().toString()
        );

    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Wrong credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong credentials");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().toString());

        return new LoginResponse(
            token,
            user.getEmail(),
            user.getFullName(),
            user.getRole().toString()
        );
    }

    public boolean validateToken(String token) {
        return jwtService.isTokenValid(token);
    }
 
    public String extractEmail(String token) {
        return jwtService.extractEmail(token);
    }
}
