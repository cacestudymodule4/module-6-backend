package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.dto.PageRequestDTO;
import com.example.module_6_back_end.dto.SalaryResponse;
import com.example.module_6_back_end.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SalaryController {
    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/api/salary")
    public ResponseEntity<Page<SalaryResponse>> getSalary(@ModelAttribute PageRequestDTO pageRequest) {
        return ResponseEntity.ok(salaryService.getSalary(pageRequest));
    }

    @GetMapping("/api/salary-csv")
    public ResponseEntity<List<SalaryResponse>> getSalaryCsv() {
        return ResponseEntity.ok().body(salaryService.getSalary());
    }
}
