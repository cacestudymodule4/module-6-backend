package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Building;
import com.example.module_6_back_end.model.JwtResponse;
import com.example.module_6_back_end.service.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/building")
@RestController
public class BuildingController {
    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping("")
    public ResponseEntity<?> getBuilding(){
        List<Building> buildings = buildingService.findAll();
        if (buildings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(buildings, HttpStatus.OK);
    }
}
