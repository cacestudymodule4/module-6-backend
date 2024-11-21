package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.GroundServices;

import java.time.LocalDate;
import java.util.List;

public interface GroundServicesService {
    List<Long> getGroundIdsByServiceId(Long serviceId);

    void deleteAllGroundId(Long serviceId);

    List<GroundServices> getGroundServicesByServicesId(Long serviceId);

    List<Ground> getGroundsNotUsingService(Long serviceId);

    void addServiceToGround(Long serviceId, Long groundId, double consumption, LocalDate startDate);

    void deleteGroundFromService(Long serviceId, Long groundId);

    void updateGroundService(Long serviceId, Long groundId, double consumption, LocalDate startDate);

    GroundServices fetchGroundDetail(Long serviceId, Long groundId);
}
