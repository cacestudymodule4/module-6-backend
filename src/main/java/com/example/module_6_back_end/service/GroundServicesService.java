package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.GroundServices;

import java.util.List;

public interface GroundServicesService {
    List<Long> getGroundIdsByServiceId(Long serviceId);

    void deleteAllGroundId(Long serviceId);

    List<GroundServices> getGroundServicesByServicesId(Long serviceId);
}
