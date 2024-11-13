package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    ServicesService servicesService;

    @GetMapping("list")
    public ResponseEntity<Page<Services>> getAllServices(@RequestParam("page") int page, @RequestParam("size") int size) {
        System.out.println("OK");
        Pageable pageable = PageRequest.of(page, size);
        System.out.println(pageable);
        try {
            return ResponseEntity.ok().body(servicesService.getAllServices(pageable));
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
