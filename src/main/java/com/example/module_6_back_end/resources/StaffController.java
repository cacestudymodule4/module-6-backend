package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService service;

    @GetMapping("/list")
    public ResponseEntity<List<Staff>> list() {
        return ResponseEntity.ok().body(service.getAllStaff());
    }
}

