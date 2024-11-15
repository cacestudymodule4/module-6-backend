package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Services updateService(Long id, Services updatedService) {
        Optional<Services> existingServiceOpt = serviceRepository.findById(id);
        if (existingServiceOpt.isEmpty()) {
            throw new IllegalArgumentException("Dịch vụ không tồn tại với ID: " + id);
        }
        Services existingServices = existingServiceOpt.get();
        existingServices.setName(updatedService.getName());
        existingServices.setPrice(updatedService.getPrice());
        existingServices.setUnit(updatedService.getUnit());
        return serviceRepository.save(existingServices);
    }

    @Override
    public Services addService(Services newService) {
        return serviceRepository.save(newService);
    }

}
