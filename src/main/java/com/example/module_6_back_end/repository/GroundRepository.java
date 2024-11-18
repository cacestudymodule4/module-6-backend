package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Ground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroundRepository extends JpaRepository<Ground, Long> {
    List<Ground> findByGroundCategory(String groundCategory);

    List<Ground> findByNameContaining(String name);

    @Query("SELECT g FROM Ground g WHERE g NOT IN (SELECT c.ground FROM Contract c)")
    List<Ground> findGroundsWithoutContract();
}
