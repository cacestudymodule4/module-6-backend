package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Services, Long> {
    boolean existsByName(String name);

    @Query("SELECT g FROM Ground g " +
            "JOIN GroundServices gs ON g.id = gs.ground.id " +
            "JOIN Services s ON gs.services.id = s.id " +
            "WHERE s.name = :serviceName")
    List<Ground> findGroundsByServiceName(@Param("serviceName") String serviceName);
}
