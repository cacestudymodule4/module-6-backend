package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Page<Customer> getAllCustomers(Pageable pageable);

    List<Customer> getCustomers();

    void deleteCustomer(Long id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer updatedCustomer);

    Customer findByIdentification(String identification);

    Customer findCustomer(Long id);

    List<Customer> findCustomerByName(String name);

    Page<Customer> searchCustomers(String name, String identification, Pageable pageable);

}
