package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByIdentification(String identification);
}
