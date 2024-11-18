package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Floor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    @Query("SELECT f FROM Floor f WHERE "
            + "(:name IS NULL OR f.name LIKE CONCAT('%', :name, '%')) "
            + "AND (:area IS NULL OR f.area = :area) "
            + "AND (:typeOfFloor IS NULL OR f.typeOfFloor LIKE CONCAT('%', :typeOfFloor, '%'))")
    Page<Floor> searchFloor(@Param("name") String name,
                            @Param("area") Double area,
                            @Param("typeOfFloor") String typeOfFloor,
                            Pageable pageable);

    boolean existsByFloorCode(String floorCode);

    Floor findByFloorCode(String floorCode);
}
