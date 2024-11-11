package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();

    boolean deleteCustomer(Long id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer updatedCustomer);
}
