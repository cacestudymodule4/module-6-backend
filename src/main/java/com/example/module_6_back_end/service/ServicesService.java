package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServicesService {
    Page<Services> getAllServices(Pageable pageable);

    void deleteServiceById(Long id);

    Services updateService(Long id, Services updatedService);

    Services addService(Services newService);

    List<Ground> getGroundsByServiceName(String serviceName);

    Services findById(Long id);
}
