package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.RoomFacilities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFacilitiesRepository extends JpaRepository<RoomFacilities, Long> {
    @Query("SELECT r FROM RoomFacilities r ORDER BY r.id DESC")
    Page<RoomFacilities> findAllRoomFacilitiesOrderByIdDesc(Pageable pageable);
}
