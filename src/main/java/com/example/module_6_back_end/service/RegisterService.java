package com.example.module_6_back_end.service;

import com.example.module_6_back_end.exception.UnauthorizedException;
import com.example.module_6_back_end.model.Role;
import com.example.module_6_back_end.model.User;
import com.example.module_6_back_end.repository.RoleRepository;
import com.example.module_6_back_end.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private RegisterService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public void registerUser(User user) {
        User auTh = userService.getCurrentUser();
        List<Role> roles = roleRepository.findByUser(auTh);
        boolean isAdmin = roles.stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedException("Bạn không có quyền thực hiện hành động này");
        }
        user.setUsername(user.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode("123456789"));
        user = userRepository.save(user);
        Role role = new Role();
        role.setUser(user);
        role.setName("STAFF");
        roleRepository.save(role);
    }

    public String getAccountStatus(String codeStaff) {
        boolean hasAccount = userRepository.existsByCodeStaff(codeStaff);
        return hasAccount ? "Đã có tài khoản" : "Chưa có tài khoản";
    }
}
