package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.dto.UserResponse;
import com.example.module_6_back_end.model.User;
import com.example.module_6_back_end.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/detail")
    public ResponseEntity<UserResponse> getUserDetail() {
        User user = userService.getCurrentUser();
        UserResponse userDTO = new UserResponse();
        BeanUtils.copyProperties(user.getStaff(), userDTO);
        return ResponseEntity.ok().body(userDTO);
    }
}
