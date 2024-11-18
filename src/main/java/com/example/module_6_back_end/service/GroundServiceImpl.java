package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.GroundServices;
import com.example.module_6_back_end.repository.GroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroundServiceImpl implements GroundService {
    @Autowired
    private GroundRepository groundRepository;

    @Override
    public List<Ground> getGrounds() {
        return groundRepository.findAll();
    }

    @Override
    public Ground getGround(Long id) {
        return groundRepository.findById(id).orElse(null);
    }
  
    @Override
    public List<Ground> findByGroundCategory(String groundCategory) {
        return groundRepository.findByGroundCategory(groundCategory);
    }

    @Override
    public void saveGround(Ground ground) {
        groundRepository.save(ground);
    }

    @Override
    public List<Ground> findByNameContaining(String name) {
        return groundRepository.findByNameContaining(name);
    }

    @Override
    public List<Ground> findGroundNotInContract() {
        return groundRepository.findGroundsWithoutContract();
    }
}
