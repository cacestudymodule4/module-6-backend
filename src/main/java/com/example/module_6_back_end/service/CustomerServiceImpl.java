package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public boolean deleteCustomer(Long id) {
//Xóa thêm contract id vì dính khóa ngoại
        customerRepository.deleteById(id);
        return true;
    }
}
