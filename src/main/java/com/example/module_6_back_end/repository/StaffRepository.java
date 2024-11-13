package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    @Query("SELECT s FROM Staff s WHERE s.name LIKE :searchTerm OR s.address LIKE :searchTerm OR s.phone LIKE :searchTerm OR s.email LIKE :searchTerm OR s.position LIKE :searchTerm")
    Page<Staff> searchStaff(@Param("searchTerm") String searchTerm, Pageable pageable);
}
