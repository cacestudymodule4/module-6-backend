package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    ServicesService servicesService;

    @GetMapping("/list")
    public ResponseEntity<Page<Services>> getAllServices(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            return ResponseEntity.ok().body(servicesService.getAllServices(pageable));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong hệ thống.");
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getServiceDetail(@PathVariable Long id) {
        Services services = servicesService.findById(id);
        if (services == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dịch vụ không tồn tại");
        }
        List<Ground> grounds = servicesService.getGroundsByServiceName(services.getName());
        Map<String, Object> response = new HashMap<>();
        response.put("service", services);
        response.put("grounds", grounds);
        return ResponseEntity.ok(response);
    }
}
