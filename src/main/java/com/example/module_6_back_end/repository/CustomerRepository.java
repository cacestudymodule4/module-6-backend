package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    boolean existsByIdentification(String identification);

    Customer findByIdentification(String identification);

    List<Customer> findByNameContaining(String name);
}
