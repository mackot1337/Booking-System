import com.booking.auth.dto.LoginRequest;
import com.booking.auth.dto.LoginResponse;
import com.booking.auth.dto.RegisterRequest;
import com.booking.auth.entity.User;
import com.booking.auth.repository.UserRepository;
import com.booking.auth.service.AuthService;
import com.booking.auth.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest("test", "test@example.com", "password", "Test User", "BUSSINESS_OWNER");
        LoginResponse response = authService.register(request);
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void testLogin() {
        RegisterRequest registerRequest = new RegisterRequest("test", "test@example.com", "password", "Test User", "BUSSINESS_OWNER");
        authService.register(registerRequest);
        LoginRequest request = new LoginRequest("test@example.com", "password");
        LoginResponse response = authService.login(request);
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void testValidateToken() {
        RegisterRequest registerRequest = new RegisterRequest("test", "test@example.com", "password", "Test User", "BUSSINESS_OWNER");
        authService.register(registerRequest);
        LoginRequest request = new LoginRequest("test@example.com", "password");
        LoginResponse response = authService.login(request);
        boolean isValid = authService.validateToken(response.getToken());
        assertEquals(true, isValid);
    }

    @Test
    void testExtractEmail() {
        RegisterRequest registerRequest = new RegisterRequest("test", "test@example.com", "password", "Test User", "BUSSINESS_OWNER");
        authService.register(registerRequest);
        LoginRequest request = new LoginRequest("test@example.com", "password");
        LoginResponse response = authService.login(request);
        String email = authService.extractEmail(response.getToken());
        assertEquals("test@example.com", email);
    }

    @Test
    void testRegisterDuplicateEmail() {
        RegisterRequest request = new RegisterRequest("test", "test@example.com", "password", "Test User", "BUSSINESS_OWNER");
        authService.register(request);
        assertThrows(RuntimeException.class, () -> authService.register(request));
    }

    @Test
    void testLoginInvalidCredentials() {
        RegisterRequest registerRequest = new RegisterRequest("test", "test@example.com", "password", "Test User", "BUSSINESS_OWNER");
        authService.register(registerRequest);
        LoginRequest request = new LoginRequest("test@example.com", "wrongpassword");
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }
}
