package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.User;
import com.example.module_6_back_end.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("api/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        System.out.println(user);
        try {
            registerService.registerUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
