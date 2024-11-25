package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.service.FloorCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/floor-category")
public class FloorCategoryController {
    @Autowired
    private FloorCategoryService floorCategoryService;

    @GetMapping("/list")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(floorCategoryService.findAll(), HttpStatus.OK);
    }
}
