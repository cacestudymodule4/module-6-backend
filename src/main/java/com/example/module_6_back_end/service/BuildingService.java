package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Building;
import com.example.module_6_back_end.repository.BuildingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    public void update(Building building) {
        buildingRepository.save(building);
    }
}
