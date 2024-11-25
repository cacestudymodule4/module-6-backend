package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Floor;
import com.example.module_6_back_end.model.FloorCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    @Query("SELECT f FROM Floor f WHERE "
            + "(:name IS NULL OR f.name LIKE CONCAT('%', :name, '%')) "
            + "AND (:areaFrom IS NULL OR f.area >= :areaFrom) "
            + "AND (:areaTo IS NULL OR f.area <= :areaTo) "
            + "AND (:floorCategory IS NULL OR f.floorCategory = :floorCategory) "
            + "ORDER BY f.area ASC")
    Page<Floor> searchFloor(@Param("name") String name,
                            @Param("areaFrom") Double areaFrom,
                            @Param("areaTo") Double areaTo,
                            @Param("floorCategory") FloorCategory floorCategory,
                            Pageable pageable);


    boolean existsByFloorCode(String floorCode);

    Floor findByFloorCode(String floorCode);

    Page<Floor> findAllByDeletedFalse(Pageable pageable);

    List<Floor> findAllByDeletedFalse();
}
