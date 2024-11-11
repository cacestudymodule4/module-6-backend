package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.repository.GroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroundServiceImpl implements GroundService {
    @Autowired
    private GroundRepository groundRepo;

    @Override
    public List<Ground> getGrounds() {
        return groundRepo.findAll();
    }

    @Override
    public Ground getGround(long id) {
        return groundRepo.findById(id).orElse(null);
    }
}
