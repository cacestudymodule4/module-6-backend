package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.GroundServices;
import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.service.GroundServicesService;
import com.example.module_6_back_end.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    ServicesService servicesService;
    @Autowired
    GroundServicesService groundServicesService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllServices(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "name", required = false) String name) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Services> services = servicesService.searchServices(name, pageable);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi lấy danh sách dịch vụ.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        try {
            System.out.println(id);
            servicesService.deleteServiceById(id);
            return ResponseEntity.ok().body("Service deleted successfully.");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> editService(@PathVariable Long id) {
        return ResponseEntity.ok().body(servicesService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody Services updatedService) {
        try {
            Services service = servicesService.updateService(id, updatedService);
            return ResponseEntity.ok(service);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addService(@RequestBody Services newService) {
        try {
            Services savedService = servicesService.addService(newService);
            return ResponseEntity.ok(savedService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getServiceDetail(@PathVariable Long id) {
        Services service = servicesService.getById(id);
        if (service == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dịch vụ không tồn tại");
        }
        List<GroundServices> groundServices = groundServicesService.getGroundServicesByServicesId(id);

        if (groundServices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có mặt bằng nào liên kết với dịch vụ này");
        }
        List<Map<String, Object>> groundsResponse = new ArrayList<>();

        for (GroundServices groundService : groundServices) {
            Map<String, Object> groundData = new HashMap<>();
            groundData.put("groundId", groundService.getGround().getId());
            groundData.put("groundName", groundService.getGround().getGroundCode());
            groundData.put("consumption", groundService.getConsumption());
            groundData.put("startDate", groundService.getStartDate());

            groundsResponse.add(groundData);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("service", service);
        response.put("grounds", groundsResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/not-using-service/{serviceId}")
    public ResponseEntity<List<Ground>> getGroundsNotUsingService(@PathVariable Long serviceId) {
        List<Ground> grounds = groundServicesService.getGroundsNotUsingService(serviceId);
        return ResponseEntity.ok(grounds);
    }

    @PostMapping("/add-service-to-gr")
    public ResponseEntity<?> addServiceToGround(
            @RequestParam Long serviceId,
            @RequestParam Long groundId,
            @RequestParam double consumption,
            @RequestParam LocalDate startDate) {
        try {
            groundServicesService.addServiceToGround(serviceId, groundId, consumption, startDate);
            return ResponseEntity.ok("Dịch vụ đã được thêm thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{serviceId}/grounds/{groundId}")
    public ResponseEntity<?> deleteGroundFromService(
            @PathVariable Long serviceId,
            @PathVariable Long groundId) {
        try {
            groundServicesService.deleteGroundFromService(serviceId, groundId);
            return ResponseEntity.ok("Xóa mặt bằng thành công.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update/{serviceId}/grounds/{groundId}")
    public ResponseEntity<Void> updateGroundService(
            @PathVariable Long serviceId,
            @PathVariable Long groundId,
            @RequestParam double consumption,
            @RequestParam LocalDate startDate) {
        groundServicesService.updateGroundService(serviceId, groundId, consumption, startDate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit/{serviceId}/grounds/{groundId}")
    public ResponseEntity<GroundServices> fetchGroundDetail(
            @PathVariable Long serviceId,
            @PathVariable Long groundId) {
        GroundServices groundServices = groundServicesService.fetchGroundDetail(serviceId, groundId);
        return ResponseEntity.ok(groundServices);
    }
}