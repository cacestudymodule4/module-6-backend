package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private GroundService groundService;

    @Override
    public Page<Services> getAllServices(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteServiceById(Long id) {
        serviceRepository.deleteAllGroundIdByServiceId(id);
        serviceRepository.deleteById(id);
    }
}
