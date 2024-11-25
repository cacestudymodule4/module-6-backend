package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.FloorCategory;

import java.util.List;

public interface FloorCategoryService {
    List<FloorCategory> findAll();
    FloorCategory findById(Long id);
}
