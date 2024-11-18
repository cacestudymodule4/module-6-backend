package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.GroundServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroundServicesRepository extends JpaRepository<GroundServices, Long> {
    @Query("SELECT gs.ground.id FROM GroundServices gs WHERE gs.services.id = :serviceId")
    List<Long> findGroundIdsByServiceId(@Param("serviceId") Long serviceId);
}
