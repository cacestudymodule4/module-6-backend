package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Role;
import com.example.module_6_back_end.model.User;
import com.example.module_6_back_end.repository.RoleRepository;
import com.example.module_6_back_end.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private RegisterService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        User newUser = userRepository.save(user);
        Role role = new Role();
        role.setUser(newUser);
        role.setName("USER");
        roleRepository.save(role);
    }
}
