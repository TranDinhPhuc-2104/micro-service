package com.example.employee.controller;

import com.example.employee.security.MockJwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        logger.info("Login attempt received for username: '{}'", username);

        // Simple mock verification based on seeds in script.sql
        String role = null;
        if ("admin".equals(username)) {
            role = "ADMIN";
        } else if ("jane".equals(username)) {
            role = "HR";
        } else if ("john".equals(username) || "michael".equals(username) || "emily".equals(username)) {
            role = "EMPLOYEE";
        }

        // For mock simplicity, accept login for any of our predefined seed users
        if (role != null) {
            String token = MockJwtUtil.generateToken(username, role);
            logger.info("Login successful for username: '{}', issued mock JWT with role: '{}'", username, role);
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            logger.warn("Login failed: Invalid credentials for username: '{}'", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class LoginResponse {
        private String token;
        private String tokenType = "Bearer";

        public LoginResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }
    }
}
