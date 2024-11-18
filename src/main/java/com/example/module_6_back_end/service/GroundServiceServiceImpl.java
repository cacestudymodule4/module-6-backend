package com.example.module_6_back_end.service;

import com.example.module_6_back_end.repository.GroundRepository;
import com.example.module_6_back_end.repository.GroundServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroundServiceServiceImpl implements GroundServicesService {
    @Autowired
    private GroundServicesRepository groundServicesRepository;

    @Override
    public List<Long> getGroundIdsByServiceId(Long serviceId) {
        return groundServicesRepository.findGroundIdsByServiceId(serviceId);
    }
    public void deleteAllGroundId(Long serviceId) {
        List<Long> groundIds = getGroundIdsByServiceId(serviceId);
        groundServicesRepository.deleteAllById(groundIds);
    }
}
