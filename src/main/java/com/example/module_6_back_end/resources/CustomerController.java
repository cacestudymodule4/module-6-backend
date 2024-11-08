package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/api/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerList = customerService.getCustomers();
        return ResponseEntity.ok(customerList);
    }

    @DeleteMapping("/api/customers/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        System.out.println(id);
        boolean ifExists = customerService.deleteCustomer(id);
        if (ifExists) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
