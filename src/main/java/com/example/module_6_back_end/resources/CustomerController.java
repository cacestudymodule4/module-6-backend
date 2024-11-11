package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> list() {
        return ResponseEntity.ok().body(service.getCustomers());
    }
}


