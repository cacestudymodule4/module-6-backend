package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Staff;
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
    public ResponseEntity<?> registerUser(@RequestBody Staff staff) {
        try {
            registerService.registerUser(staff);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
