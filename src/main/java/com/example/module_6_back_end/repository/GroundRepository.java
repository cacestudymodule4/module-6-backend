package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.GroundServices;
import com.example.module_6_back_end.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroundRepository extends JpaRepository<Ground, Long> {
    List<Ground> findByGroundCategory(String groundCategory);
    List<Ground> findByNameContaining(String name);
    @Query(value = "SELECT g.* FROM ground g JOIN ground_service gs ON g.id = gs.ground_id JOIN services s ON s.id = gs.services_id WHERE s.id = :serviceId", nativeQuery = true)
    List<Ground> findGroundsByServiceId(@Param("serviceId") Long serviceId);

}