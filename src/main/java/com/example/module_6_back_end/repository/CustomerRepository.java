package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    boolean existsByIdentification(String identification);

    Customer findByIdentification(String identification);

<<<<<<< HEAD
    List<Customer> findByNameContaining(String name);
=======
    Page<Customer> findByNameContaining(String name, Pageable pageable);

    Page<Customer> findByIdentificationContaining(String identification, Pageable pageable);

    Page<Customer> findByNameContainingAndIdentificationContaining(String name, String identification, Pageable pageable);
>>>>>>> 2cda348c8dac14c917502e8b9104706a4aa0677f
}

