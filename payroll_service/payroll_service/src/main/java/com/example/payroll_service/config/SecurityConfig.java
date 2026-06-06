package com.example.payroll_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {
    @Value("${app.jwt.secret}")
    private String signerKey;

    // 1. Thêm 'throws Exception' ở đây để xử lý ngoại lệ từ security.build()
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        security.oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.decoder(jwtDecoder())));

        security.csrf(AbstractHttpConfigurer::disable);
        return security.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(),"HmacSHA256"); // Sửa lại chuẩn HS256
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}