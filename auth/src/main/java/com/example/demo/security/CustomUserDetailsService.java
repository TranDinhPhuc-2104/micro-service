package com.example.demo.security;


import com.example.demo.entites.CustomUserDetails;
import com.example.demo.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch user from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        // 2. Convert to Spring Security's User object
        return new CustomUserDetails(
                user.getId(),              // <--- Lấy ID từ entity và truyền vào đây
                user.getUsername(),
                user.getPasswordHash(),
                user.getEnabled() != null ? user.getEnabled() : false, // Check enabled
                true,                      // accountNonExpired
                true,                      // credentialsNonExpired
                true,                      // accountNonLocked
                authorities
        );
    }
}