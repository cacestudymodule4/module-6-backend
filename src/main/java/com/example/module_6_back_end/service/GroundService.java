package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Ground;

import java.util.List;

public interface GroundService {
    List<Ground> getGrounds();

    Ground getGround(Long id);

    List<Ground> findByGroundCategory(String groundCategory);

    void saveGround(Ground ground);

    List<Ground> findByNameContaining(String name);

    List<Ground> findGroundNotInContract();
}
