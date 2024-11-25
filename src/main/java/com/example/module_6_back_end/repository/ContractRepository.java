package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c WHERE "
            + "(:startDate IS NULL OR c.startDate >= :startDate) "
            + "AND (:endDate IS NULL OR c.endDate <= :endDate) "
            + "AND (:taxCode IS NULL OR c.code LIKE CONCAT('%', :taxCode, '%')) "
            + "AND (:nameCustomer IS NULL OR c.customer.name LIKE CONCAT('%', :nameCustomer, '%')) "
            + "AND (:staffId IS NULL OR c.staff.id = :staffId)")
    Page<Contract> searchContract(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate,
                                  @Param("taxCode") String taxCode,
                                  @Param("nameCustomer") String nameCustomer,
                                  @Param("staffId") Long staffId,
                                  Pageable pageable);

    @Query("SELECT c FROM Contract c WHERE "
            + "(:startDate IS NULL OR c.startDate >= :startDate) "
            + "AND (:endDate IS NULL OR c.endDate <= :endDate) "
            + "AND (:taxCode IS NULL OR c.code LIKE CONCAT('%', :taxCode, '%')) "
            + "AND (:nameCustomer IS NULL OR c.customer.name LIKE CONCAT('%', :nameCustomer, '%')) ")
    Page<Contract> searchAllContract(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate,
                                  @Param("taxCode") String taxCode,
                                  @Param("nameCustomer") String nameCustomer,
                                  Pageable pageable);

    List<Contract> findByCustomer(Customer customer);

    List<Contract> findByStaff(Staff staff);

    @Query("SELECT c FROM Contract c WHERE c.startDate >= :startDate AND c.endDate <= :endDate")
    List<Contract> findByStartDateAndEndDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Contract c WHERE c.startDate >= :startDate")
    List<Contract> findByStartDate(@Param("startDate") LocalDate startDate);

    @Query("SELECT c FROM Contract c WHERE c.endDate <= :endDate")
    List<Contract> findByEndDate(@Param("endDate") LocalDate endDate);

    Page<Contract> findByEndDateLessThanAndStaffId(LocalDate date,Long staffId, Pageable pageable);

    Page<Contract> findByStartDateLessThanEqualAndEndDateGreaterThanAndStaffId(LocalDate startDate, LocalDate endDate,Long id, Pageable pageable);

    Page<Contract> findByStartDateGreaterThanAndStaffId(LocalDate date, Long staffId, Pageable pageable);

    Page<Contract> findByStaffIdOrderByIdDesc(Long staffId, Pageable pageable);

    Page<Contract> findByEndDateLessThan(LocalDate date, Pageable pageable);

    Page<Contract> findByStartDateLessThanEqualAndEndDateGreaterThan(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Contract> findByStartDateGreaterThan(LocalDate date, Pageable pageable);

    Page<Contract> findAllByOrderByIdDesc(Pageable pageable);


    @Query("SELECT g FROM Ground g " +
            "LEFT JOIN Contract c ON c.ground = g " +
            "GROUP BY g " +
            "HAVING (COUNT(c) < 2) AND " +
            "(MAX(c.endDate) <= :oneMonthFromNow OR COUNT(c) = 0)")
    List<Ground> findGroundsWithConditions(@Param("oneMonthFromNow") LocalDate oneMonthFromNow);
    
}
