package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Ground;

import java.util.List;

public interface GroundService {
    List<Ground> getGrounds();

    Ground getGround(long id);
}
