package com.example.module_6_back_end.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @GetMapping("/api/report")
    public ResponseEntity<?> report() {
        return ResponseEntity.ok().build();
    }
}
