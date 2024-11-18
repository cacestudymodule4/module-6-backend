package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.GroundServices;
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
    @Override
    public void deleteAllGroundId(Long serviceId) {
        List<Long> groundIds = getGroundIdsByServiceId(serviceId);
        groundServicesRepository.deleteAllById(groundIds);
    }
    @Override
    public List<GroundServices> getGroundServicesByServicesId(Long serviceId) {
        return groundServicesRepository.findByServicesId(serviceId);
    }
}
