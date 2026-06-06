package com.example.attendance_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 1. Thêm 2 dòng import này để sửa lỗi "Cannot resolve symbol"
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {
    @Value("${app.jwt.secret}")
    String signerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{
            security.authorizeHttpRequests(auth->auth.anyRequest().authenticated());
            security.oauth2ResourceServer(oath2-> oath2.jwt(jwt-> jwt.decoder(jwtDecoder())));
        security.csrf(AbstractHttpConfigurer::disable);
            return security.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS256");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}
