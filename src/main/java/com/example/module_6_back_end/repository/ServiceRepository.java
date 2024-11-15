package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository extends JpaRepository<Services, Long> {
    @Modifying
    @Query(value = "DELETE FROM ground_services WHERE services_id = :serviceId", nativeQuery = true)
    void deleteAllGroundIdByServiceId(@Param("serviceId") Long serviceId);
}
