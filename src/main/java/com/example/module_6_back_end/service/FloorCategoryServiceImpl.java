package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.FloorCategory;
import com.example.module_6_back_end.repository.FloorCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorCategoryServiceImpl implements FloorCategoryService {
    @Autowired
    private FloorCategoryRepository floorCategoryRepository;

    @Override
    public List<FloorCategory> findAll() {
        return floorCategoryRepository.findAll();
    }

    @Override
    public FloorCategory findById(Long id) {
        return floorCategoryRepository.findById(id).get();
    }
}
