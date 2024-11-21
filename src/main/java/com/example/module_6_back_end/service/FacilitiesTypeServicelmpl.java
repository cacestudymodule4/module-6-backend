package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.FacilitiesType;
import com.example.module_6_back_end.repository.FacilitiesTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilitiesTypeServicelmpl implements FacilitiesTypeService {
    @Autowired
    private FacilitiesTypeRepository facilitiesTypeRepository;

    @Override
    public List<FacilitiesType> getAllFacilitiesType() {
        return facilitiesTypeRepository.findAll();
    }
}
