package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.repository.ContractRepository;
import com.example.module_6_back_end.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ContractService contractService;


    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = findCustomer(id);
        if (customer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }
        contractService.deleteContracts(customer);
        customerRepository.deleteById(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email bị trùng lặp");
        }
        if (customerRepository.existsByIdentification(customer.getIdentification())) {
            throw new IllegalArgumentException("CMND bị trùng lặp");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng"));
        if (!existingCustomer.getEmail().equals(updatedCustomer.getEmail()) &&
                customerRepository.existsByEmail(updatedCustomer.getEmail())) {
            throw new IllegalArgumentException("Email bị trùng lặp");
        }
        if (!existingCustomer.getIdentification().equals(updatedCustomer.getIdentification()) &&
                customerRepository.existsByIdentification(updatedCustomer.getIdentification())) {
            throw new IllegalArgumentException("Chứng minh nhân dân bị trùng lặp");
        }
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setBirthday(updatedCustomer.getBirthday());
        existingCustomer.setIdentification(updatedCustomer.getIdentification());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setCompany(updatedCustomer.getCompany());
        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer findByIdentification(String identification) {
        return customerRepository.findByIdentification(identification);
    }

    @Override
    public Customer findCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override

    public List<Customer> findCustomerByName(String name) {
        return customerRepository.findByNameContaining(name);

    }
    @Override
    public Page<Customer> searchCustomers(String name, String identification, Pageable pageable) {
        if (name != null && identification != null) {
            return customerRepository.findByNameContainingAndIdentificationContaining(name, identification, pageable);
        } else if (name != null) {
            return customerRepository.findByNameContaining(name, pageable);
        } else if (identification != null) {
            return customerRepository.findByIdentificationContaining(identification, pageable);
        } else {
            return customerRepository.findAll(pageable);
        }

    }
}
