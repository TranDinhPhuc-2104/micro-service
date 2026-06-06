package com.example.employee.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class MockJwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(MockJwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            if (!token.isEmpty()) {
                String username = null;
                String role = null;

                if (token.contains(".")) {
                    MockJwtUtil.ParsedToken parsed = MockJwtUtil.parseToken(token);
                    if (parsed != null) {
                        username = parsed.getUsername();
                        role = parsed.getRole();
                    } else {
                        logger.warn("Token structure matches JWT format but could not be verified by Java JWT Library.");
                    }
                }

                if (username == null || role == null) {
                    if ("mock-admin-token".equals(token)) {
                        username = "admin";
                        role = "ADMIN";
                    } else if ("mock-hr-token".equals(token)) {
                        username = "hr_specialist";
                        role = "HR";
                    } else if ("mock-employee-token".equals(token)) {
                        username = "employee_user";
                        role = "EMPLOYEE";
                    }
                }
                
                if (username != null && role != null) {
                    MockUserPrincipal principal = new MockUserPrincipal(username, role);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            principal, null, principal.getAuthorities());
                    
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.warn("Security check failed: Authorization token '{}' is invalid or expired. Rejecting credentials.", token);
                }
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
