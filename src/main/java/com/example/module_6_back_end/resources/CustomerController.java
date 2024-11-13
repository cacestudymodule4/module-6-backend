package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/api/customers")
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerService.getAllCustomers(pageable);
    }

    @DeleteMapping("/api/customers/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/api/customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dữ liệu không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong hệ thống.");
        }
    }

    @PutMapping("/api/customers/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        try {
            Customer customer = customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dữ liệu không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong hệ thống.");
        }
    }

    @GetMapping("/api/customer/list")
    public ResponseEntity<List<Customer>> list() {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }

    @GetMapping("/api/customers/{customerId}/services")
    public ResponseEntity<List<Services>> getServicesByCustomerId(@PathVariable Long customerId) {
        List<Services> services = customerService.getServicesByCustomerId(customerId);
        return ResponseEntity.ok(services);
    }
    @GetMapping("/api/customers/search")
    public Page<Customer> searchCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String identification,
            Pageable pageable) {
        return customerService.searchCustomers(name, identification, pageable);
    }
}
