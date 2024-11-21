package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.RoomFacilities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFacilitiesRepository extends JpaRepository<RoomFacilities, Long> {
    @Query("SELECT r FROM RoomFacilities r ORDER BY r.id DESC")
    Page<RoomFacilities> findAllRoomFacilitiesOrderByIdDesc(Pageable pageable);
    @Query("SELECT f FROM RoomFacilities f WHERE "
            + "(:facilitiesType IS NULL OR f.facilitiesType.name LIKE CONCAT('%', :facilitiesType, '%')) "
            + "AND (:facilitiesName IS NULL OR f.name Like CONCAT('%', :facilitiesName, '%')) "
            + "AND (:ground IS NULL OR f.ground.name LIKE CONCAT('%', :ground, '%')) ")
    Page<RoomFacilities> searchRoomFacilitiesBy(@Param("facilitiesType") String facilitiesType,
                                  @Param("facilitiesName") String facilitiesName,
                                  @Param("ground") String ground,
                                  Pageable pageable);
}
