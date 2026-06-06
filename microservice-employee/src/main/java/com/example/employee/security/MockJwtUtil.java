package com.example.employee.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class MockJwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(MockJwtUtil.class);
    private static final String SECRET = "mock-secret-key-1234567890-mock-secret-key-1234567890";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final String ISSUER = "employee-service";

    public static String generateToken(String username, String role) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // Valid for 1 hour
                .sign(ALGORITHM);
    }

    public static ParsedToken parseToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String username = jwt.getSubject();
            String role = jwt.getClaim("role").asString();
            if (username != null && role != null) {
                return new ParsedToken(username, role);
            }
        } catch (Exception e) {
            logger.warn("Mock JWT verification failed: {}", e.getMessage());
        }
        return null;
    }

    public static class ParsedToken {
        private final String username;
        private final String role;

        public ParsedToken(String username, String role) {
            this.username = username;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }
    }
}
