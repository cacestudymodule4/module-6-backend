package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByNameContainingIgnoreCase(String name);

    List<Staff> findByNameContaining(String name);

    boolean existsByEmail(String email);
}
