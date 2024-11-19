package com.example.module_6_back_end.dto;

import com.example.module_6_back_end.model.Role;
import com.example.module_6_back_end.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserInfo implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfo(User User, List<Role> roles) {
        username = User.getUsername();
        password = User.getPassword();
        authorities = new ArrayList<>();
        if (roles != null) {
            for (Role role : roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
