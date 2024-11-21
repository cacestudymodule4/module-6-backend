package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Ground;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroundRepository extends JpaRepository<Ground, Long> {
    @Query("SELECT g FROM Ground g WHERE "
            + "(:groundCode IS NULL OR g.groundCode LIKE CONCAT('%', :groundCode, '%')) "
            + "AND (:area IS NULL OR g.area = :area) "
            + "AND (:price IS NULL OR g.price = :price)")
    Page<Ground> searchGround(@Param("groundCode") String groundCode,
                            @Param("area") Double area,
                            @Param("price") Double price,
                            Pageable pageable);

    Page<Ground> findAllByDeletedFalse(Pageable pageable);

    List<Ground> findByStatus(Boolean status);

    List<Ground> findByGroundCodeContaining(String groundCode);

    @Query("SELECT g FROM Ground g WHERE g NOT IN (SELECT c.ground FROM Contract c)")
    List<Ground> findGroundsWithoutContract();

    boolean existsByGroundCode(String groundCode);

    Ground findByGroundCode(String groundCode);
}
