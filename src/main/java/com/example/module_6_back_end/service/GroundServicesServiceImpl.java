package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.GroundServices;
import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.repository.GroundRepository;
import com.example.module_6_back_end.repository.GroundServicesRepository;
import com.example.module_6_back_end.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GroundServicesServiceImpl implements GroundServicesService {
    @Autowired
    private GroundServicesRepository groundServicesRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private GroundRepository groundRepository;

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

    @Override
    public List<Ground> getGroundsNotUsingService(Long serviceId) {
        return groundServicesRepository.findGroundsNotUsingService(serviceId);
    }

    @Override
    public void addServiceToGround(Long serviceId, Long groundId, double consumption, LocalDate startDate) {
        Services services = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Dịch vụ không tồn tại"));
        Ground ground = groundRepository.findById(groundId).orElseThrow(() -> new RuntimeException("Mặt bằng không tồn tại"));
        GroundServices groundServices = new GroundServices();
        groundServices.setGround(ground);
        groundServices.setServices(services);
        groundServices.setConsumption(consumption);
        groundServices.setStartDate(startDate);
        groundServicesRepository.save(groundServices);
    }

    @Override
    public void deleteGroundFromService(Long serviceId, Long groundId) {
        Services services = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Dịch vụ không tồn tại."));
        Ground ground = groundRepository.findById(groundId)
                .orElseThrow(() -> new IllegalArgumentException("Mặt bằng không tồn tại."));
        GroundServices groundServices = groundServicesRepository.findByServicesAndGround(services, ground)
                .orElseThrow(() -> new IllegalArgumentException("Mặt bằng không tồn tại trong dịch vụ này"));
        groundServicesRepository.delete(groundServices);

    }
    @Override
    public GroundServices fetchGroundDetail(Long serviceId, Long groundId) {
        Services services = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Dịch vụ không tồn tại."));
        Ground ground = groundRepository.findById(groundId)
                .orElseThrow(() -> new IllegalArgumentException("Mặt bằng không tồn tại."));
        GroundServices groundServices = groundServicesRepository.findByServicesAndGround(services, ground)
                .orElseThrow(() -> new IllegalArgumentException("Mặt bằng không tồn tại trong dịch vụ này"));
        String groundName = groundRepository.findById(groundId)
                .map(Ground::getName)
                .orElse("Tên mặt bằng không tồn tại");
        groundServices.getGround().setName(groundName);
        return groundServices;
    }

    @Override
    public void updateGroundService(Long serviceId, Long groundId, double consumption, LocalDate startDate) {

        Services services = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Dịch vụ không tồn tại."));
        Ground ground = groundRepository.findById(groundId)
                .orElseThrow(() -> new IllegalArgumentException("Mặt bằng không tồn tại."));
        GroundServices groundServices = groundServicesRepository.findByServicesAndGround(services, ground)
                .orElseThrow(() -> new IllegalArgumentException("Mặt bằng không tồn tại trong dịch vụ này"));
        groundServices.setConsumption(consumption);
        groundServices.setStartDate(startDate);
        groundServicesRepository.save(groundServices);
    }
}
