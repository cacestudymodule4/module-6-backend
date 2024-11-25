package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.GroundCategory;
import com.example.module_6_back_end.repository.GroundCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroundCategoryServiceImpl implements GroundCategoryService {
    @Autowired
    private GroundCategoryRepository groundCategoryRepository;

    @Override
    public List<GroundCategory> getAllGroundCategories() {
        return groundCategoryRepository.findAll();
    }
}
