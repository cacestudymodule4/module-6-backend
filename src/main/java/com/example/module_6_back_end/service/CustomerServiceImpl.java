package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Customer;
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

    public Page<Customer> getAllCustomers(Pageable pageable, Customer newCustomer) {
        return customerRepository.findAllCustomersSorted(pageable);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAllCustomersSortedByIdDesc();
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = getCustomer(id);
        if (customer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng");
        }
        customer.setIsDisabled(true);
        customerRepository.save(customer);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email bị trùng lặp");
        }
        if (customerRepository.existsByPhone(customer.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại bị trùng lặp");
        }
        Customer foundCustomer = customerRepository.findCustomerByIdentification(customer.getIdentification());
        if (foundCustomer != null) {
            if (foundCustomer.getIsDisabled()) {
                throw new IllegalArgumentException("a");
            } else {
                throw new IllegalArgumentException("Số CMND đã tồn tại.");
            }
        }
        customer.setIsDisabled(false);
        return customerRepository.save(customer);
    }

    @Override
    public Customer restoreCustomer(Customer customer) {
        Customer foundCustomer = customerRepository.findCustomerByIdentification(customer.getIdentification());
        if (foundCustomer != null && foundCustomer.getIsDisabled()) {
            foundCustomer.setIsDisabled(false);
            return customerRepository.save(foundCustomer);
        }
        return null;
    }

    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng"));
        if (!existingCustomer.getEmail().equals(updatedCustomer.getEmail()) &&
                customerRepository.existsByEmail(updatedCustomer.getEmail())) {
            throw new IllegalArgumentException("Email bị trùng lặp");
        }
//        if (!existingCustomer.getIdentification().equals(updatedCustomer.getIdentification()) &&
//                customerRepository.existsByIdentification(updatedCustomer.getIdentification())) {
//            throw new IllegalArgumentException("Chứng minh nhân dân bị trùng lặp");
//        }
        if (!existingCustomer.getPhone().equals(updatedCustomer.getPhone()) &&
                customerRepository.existsByPhone(updatedCustomer.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại bị trùng lặp");
        }
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setBirthday(updatedCustomer.getBirthday());
        existingCustomer.setIdentification(updatedCustomer.getIdentification());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setCompany(updatedCustomer.getCompany());
        existingCustomer.setGender(updatedCustomer.getGender());
        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> getCustomerByName(String name) {
        return customerRepository.findByNameContaining(name);
    }

    @Override
    public Page<Customer> searchCustomers(String name, String identification, Pageable pageable) {
        if (name != null && identification != null) {
            return customerRepository.findByNameAndIdentification(name, identification, pageable);
        } else if (name != null) {
            return customerRepository.findByNameContaining(name, pageable);
        } else if (identification != null) {
            return customerRepository.findByIdentificationContaining(identification, pageable);
        } else {
            return customerRepository.findAll(pageable);
        }
    }
}
