package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();

    void deleteCustomer(Long id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer updatedCustomer);

    Customer findByIdentification(String identification);

    Customer findCustomer(Long id);

    List<Customer> findCustomerByName(String name);
}
