package com.example.module_6_back_end.service;

import com.example.module_6_back_end.exception.ValidationException;
import com.example.module_6_back_end.model.Role;
import com.example.module_6_back_end.model.User;
import com.example.module_6_back_end.repository.RoleRepository;
import com.example.module_6_back_end.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> errors = new HashMap<>();

        if (userRepository.existsByUsername(user.getUsername())) {
            errors.put("username", "Tên người dùng đã tồn tại");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            errors.put("email", "Email đã tồn tại");
        }
        if (userRepository.existsByPhone(user.getPhone())) {
            errors.put("phone", "Số điện thoại đã tồn tại");
        }
        if (userRepository.existsByIdentification(user.getIdentification())) {
            errors.put("identification", "CMND/CCCD đã tồn tại");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        User newUser = userRepository.save(user);
        Role role = new Role();
        role.setUser(newUser);
        role.setName("USER");
        roleRepository.save(role);
    }
}
