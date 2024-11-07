package com.example.module_6_back_end.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String token, String email, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.email = email;
        this.authorities = authorities;
    }
}
