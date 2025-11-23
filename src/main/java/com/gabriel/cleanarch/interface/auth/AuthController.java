package com.gabriel.cleanarch.interfaceauth;

import com.gabriel.cleanarch.application.auth.AuthService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user.
     * <p>
     * This endpoint receives a request with the user's email and password, and returns a JWT token
     * that can be used to authenticate the user in future requests.
     * <p>
     * The returned token is valid for 1 hour.
     * <p>
     * If the user is already registered, a RuntimeException is thrown with the message "Email already registered".
     * @param request the request containing the user's email and password
     * @return a response containing the JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
        String token = authService.register(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    /**
     * Logs in a user and returns a JWT token that can be used to authenticate the user in future requests.
     * <p>
     * The returned token is valid for 1 hour.
     * <p>
     * If the user is not found, a RuntimeException is thrown with the message "User not found".
     * @param request the request containing the user's email and password
     * @return a response containing the JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Data
    public static class RegisterRequest {
        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @Data
    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @Data
    public static class TokenResponse {
        private final String token;
    }
}
