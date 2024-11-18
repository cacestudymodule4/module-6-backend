package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private GroundServicesService groundServicesService;

    @Override
    public Page<Services> getAllServices(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }
    @Override
    public Page<Services> searchServices(String name, Pageable pageable) {
        if (name == null || name.isBlank()) {
            return serviceRepository.findAll(pageable); // Nếu không có từ khóa, trả về toàn bộ danh sách
        }
        return serviceRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public void deleteServiceById(Long id) {
        groundServicesService.deleteAllGroundId(id);
        serviceRepository.deleteById(id);
    }

    @Override
    public Services updateService(Long id, Services updatedService) {
        Optional<Services> existingServiceOpt = serviceRepository.findById(id);
        if (existingServiceOpt.isEmpty()) {
            throw new IllegalArgumentException("Dịch vụ không tồn tại với ID: " + id);
        }
        boolean nameExists = serviceRepository.existsByName(updatedService.getName());
        Services existingService = existingServiceOpt.get();
        if (nameExists && !existingService.getName().equals(updatedService.getName())) {
            throw new IllegalArgumentException("Tên dịch vụ đã tồn tại: " + updatedService.getName());
        }
        existingService.setName(updatedService.getName());
        existingService.setPrice(updatedService.getPrice());
        existingService.setUnit(updatedService.getUnit());

        return serviceRepository.save(existingService);
    }


    @Override
    public Services addService(Services newService) {
        if (serviceRepository.existsByName(newService.getName())) {
            throw new IllegalArgumentException("Dịch vụ với tên này đã tồn tại.");
        }
        return serviceRepository.save(newService);
    }



    @Override
    public Services findById(Long id) {
        Optional<Services> optionalService = serviceRepository.findById(id);
        if (optionalService.isPresent()) {
            return optionalService.get();
        } else {
            throw new RuntimeException("Dịch vụ không tồn tại với ID: " + id);
        }
    }
}
