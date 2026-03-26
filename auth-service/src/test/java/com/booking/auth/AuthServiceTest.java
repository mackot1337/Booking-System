package com.booking.auth;
import com.booking.auth.dto.LoginRequest;
import com.booking.auth.dto.LoginResponse;
import com.booking.auth.dto.RegisterRequest;
import com.booking.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest("test", "test@example.com", "password", "Test User", "BUSSINESS_OWNER");
        LoginResponse response = authService.register(request);
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void testLogin() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        LoginResponse response = authService.login(request);
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void testValidateToken() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        LoginResponse response = authService.login(request);
        boolean isValid = authService.validateToken(response.getToken());
        assertEquals(true, isValid);
    }

    @Test
    void testExtractEmail() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        LoginResponse response = authService.login(request);
        String email = authService.extractEmail(response.getToken());
        assertEquals("test@example.com", email);
    }

    @Test
    void testRegisterDuplicateEmail() {
        RegisterRequest request = new RegisterRequest("test", "test@example.com", "password", "Test User", "BUSSINESS_OWNER");
        assertThrows(RuntimeException.class, () -> authService.register(request));
    }

    @Test
    void testLoginInvalidCredentials() {
        LoginRequest request = new LoginRequest("test@example.com", "wrongpassword");
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }
}
