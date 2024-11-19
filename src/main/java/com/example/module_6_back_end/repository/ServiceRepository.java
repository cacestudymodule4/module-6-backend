package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Services, Long> {
    boolean existsByName(String name);

    Page<Services> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
