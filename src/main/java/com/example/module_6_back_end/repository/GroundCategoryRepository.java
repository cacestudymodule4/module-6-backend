package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.GroundCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroundCategoryRepository extends JpaRepository<GroundCategory, Long> {
}
