package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.User;
import com.example.module_6_back_end.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

<<<<<<< HEAD
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Staff staff) {
=======
    @PostMapping("api/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
>>>>>>> 22244462b83c4e2eaf1e00e73c8dbe0198b51943
        try {
            registerService.registerUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
