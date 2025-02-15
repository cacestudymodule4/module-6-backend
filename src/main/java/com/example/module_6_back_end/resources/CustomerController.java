package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public ResponseEntity<Page<Customer>> getAllCustomers(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Customer> customers = customerService.getAllCustomers(pageable, null);
            return ResponseEntity.ok().body(customers);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong hệ thống.");
        }
    }

    @PostMapping("/restore")
    public ResponseEntity<?> restoreCustomer(@RequestBody Customer customer) {
        try {
            Customer restoredCustomer = customerService.restoreCustomer(customer);
            if (restoredCustomer != null) {
                return ResponseEntity.ok(restoredCustomer);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Không tìm thấy khách hàng hoặc khách hàng không bị vô hiệu hóa");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        System.out.println(id);
        try {
            Customer customer = customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong hệ thống.");
        }
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> editCustomer(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getCustomer(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Customer>> searchCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String identification,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Customer> result = customerService.searchCustomers(name, identification, pageable);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findCus")
    public ResponseEntity<List<Customer>> findCustomers(
            @RequestParam String searchCus
    ) {
        return ResponseEntity.ok().body(customerService.getCustomerByName(searchCus));
    }

    @GetMapping("/list-add")
    public ResponseEntity<List<Customer>> listCustomersAdd() {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }
}

