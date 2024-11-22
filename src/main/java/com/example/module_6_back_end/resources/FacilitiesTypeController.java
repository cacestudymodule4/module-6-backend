package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.FacilitiesType;
import com.example.module_6_back_end.service.FacilitiesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/facilities-type")
public class FacilitiesTypeController {
    private final FacilitiesTypeService facilitiesTypeService;

    @Autowired
    public FacilitiesTypeController(FacilitiesTypeService facilitiesTypeService) {
        this.facilitiesTypeService = facilitiesTypeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<FacilitiesType>> list() {
        return ResponseEntity.ok().body(facilitiesTypeService.getAllFacilitiesType());
    }

}
