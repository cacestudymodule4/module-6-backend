package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.dto.ReportRequest;
import com.example.module_6_back_end.exception.UnauthorizedException;
import com.example.module_6_back_end.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/api/report")
    public ResponseEntity<?> report(@RequestBody ReportRequest reportRequest) {
        try {
            return ResponseEntity.ok().body(reportService.getRevenue(reportRequest));
        } catch (UnauthorizedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
