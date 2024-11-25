package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.dto.PageRequestDTO;
import com.example.module_6_back_end.exception.UnauthorizedException;
import com.example.module_6_back_end.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalaryController {
    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/api/salary")
    public ResponseEntity<?> getSalary(@ModelAttribute PageRequestDTO pageRequest) {
        try {
            return ResponseEntity.ok(salaryService.getSalary(pageRequest));
        } catch (UnauthorizedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/salary-csv")
    public ResponseEntity<?> getSalaryCsv() {
        try {
            return ResponseEntity.ok().body(salaryService.getSalary());
        } catch (UnauthorizedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
