package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s WHERE " +
            "(:codeStaff IS NULL OR s.codeStaff LIKE :codeStaff)" +
            "AND (:name Is NULL OR s.name LIKE :name)" +
            "AND (:position IS NULL OR s.position.name LIKE :position)"
    )
    Page<Staff> findByCodeStaffOrNameOrPosition(@Param("codeStaff") String codeStaff,
                                                @Param("name") String name,
                                                @Param("position") String position,
                                                Pageable pageable);

    List<Staff> findByNameContaining(String name);

    @Query("SELECT s FROM Staff s WHERE (s.codeStaff LIKE :q OR s.name LIKE :q OR s.position.name LIKE :q) AND s.position.name LIKE :positionName AND s.isDisabled = FALSE")
    Page<Staff> findStaff(@Param("q") String q, Pageable pageable, @Param("positionName") String positionName);

    boolean existsByIdentification(String identification);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByCodeStaff(String codeStaff);

    Staff findByIdentification(String identification);

    List<Staff> findByCodeStaffNotContaining(String code);

    Page<Staff> findAllByIsDisabledFalse(Pageable pageable);
}
