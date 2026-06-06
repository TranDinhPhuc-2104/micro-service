package com.example.demo.controller;


import com.example.demo.dto.SignupRequest;
import com.example.demo.entites.Role;
import com.example.demo.entites.User;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginRequest) {

        // 1. Authenticate the user credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.get("username"),
                        loginRequest.get("password")
                )
        );

        // 2. Set authentication in context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generate JWT Token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 4. Return token to the client
        return ResponseEntity.ok(Map.of(
                "token", jwt,
                "type", "Bearer"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        // 1. Check if username is already taken
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error: Username is already taken!"));
        }

        // 2. Create new user entity
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmployeeId(signUpRequest.getEmployeeId());
        user.setEnabled(true);

        // Hash the password before saving
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));

        // 3. Assign default role (e.g., "USER")
        // Note: Make sure this role actually exists in your 'roles' database table!
        Role userRole = roleRepository.findByRoleName("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found in database."));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        // 4. Save user to database
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }}

