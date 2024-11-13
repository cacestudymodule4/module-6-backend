package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    boolean existsByIdentification(String identification);

    Customer findByIdentification(String identification);

    Page<Customer> findByNameContaining(String name, Pageable pageable);

    Page<Customer> findByIdentificationContaining(String identification, Pageable pageable);

    Page<Customer> findByNameContainingAndIdentificationContaining(String name, String identification, Pageable pageable);
}

