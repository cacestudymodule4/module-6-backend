package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Services, Long> {
    boolean existsByName(String name);

    Page<Services> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
