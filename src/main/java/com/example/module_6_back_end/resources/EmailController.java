package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendMail(@RequestBody Contract contract) {
        try {
            emailService.sendMail(contract);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
