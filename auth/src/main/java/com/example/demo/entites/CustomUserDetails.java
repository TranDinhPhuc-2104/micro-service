package com.example.demo.entites;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class CustomUserDetails extends User {

    // Assuming your ID is of type Long. Change to Integer/UUID if necessary.
    private final Long id;

    public CustomUserDetails(Long id, String username, String password, boolean enabled,
                             boolean accountNonExpired, boolean credentialsNonExpired,
                             boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        // Pass standard fields up to Spring Security's User class
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    // Getter so you can retrieve the ID later
    public Long getId() {
        return id;
    }
}
