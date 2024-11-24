package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.service.GroundCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ground-category")
public class GroundCategoryController {
    @Autowired
    private GroundCategoryService groundCategoryService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllGroundCategory() {
        return new ResponseEntity<>(groundCategoryService.getAllGroundCategories(), HttpStatus.OK);
    }
}
