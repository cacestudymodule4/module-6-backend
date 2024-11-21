package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.GroundServices;
import com.example.module_6_back_end.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroundServicesRepository extends JpaRepository<GroundServices, Long> {
    @Query("SELECT gs.ground.id FROM GroundServices gs WHERE gs.services.id = :serviceId")
    List<Long> findGroundIdsByServiceId(@Param("serviceId") Long serviceId);

    List<GroundServices> findByServicesId(Long serviceId);

    @Query("SELECT g FROM Ground g WHERE g.id NOT IN " +
            "(SELECT gs.ground.id FROM GroundServices gs WHERE gs.services.id = :servicesId)")
    List<Ground> findGroundsNotUsingService(@Param("servicesId") Long serviceId);

    Optional<GroundServices> findByServicesAndGround(Services services, Ground ground);
}
