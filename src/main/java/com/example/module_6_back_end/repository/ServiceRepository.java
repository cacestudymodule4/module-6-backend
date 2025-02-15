package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceRepository extends JpaRepository<Services, Long> {
    @Query(value = "SELECT * FROM services ORDER BY SUBSTRING_INDEX(name, ' ', -1) COLLATE utf8mb4_vietnamese_ci ASC",
            countQuery = "SELECT COUNT(*) FROM services",
            nativeQuery = true)
    Page<Services> findAllServicesSorted(Pageable pageable);
    boolean existsByName(String name);

    Page<Services> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
