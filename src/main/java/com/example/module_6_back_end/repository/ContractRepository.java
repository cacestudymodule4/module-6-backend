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
            + "AND (:nameCustomer IS NULL OR c.customer.name LIKE CONCAT('%', :nameCustomer, '%'))")
    Page<Contract> searchContract(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate,
                                  @Param("taxCode") String taxCode,
                                  @Param("nameCustomer") String nameCustomer,
                                  Pageable pageable);

    List<Contract> findByCustomer(Customer customer);

    @Query("SELECT s FROM Services s " +
            "JOIN Ground g ON g.id = s.id " +
            "JOIN Contract c ON c.ground.id = g.id " +
            "WHERE c.customer.id = :customerId")
    List<Services> findServicesByCustomerId(@Param("customerId") Long customerId);

    List<Contract> findByStaff(Staff staff);

    @Query("SELECT c FROM Contract c WHERE c.startDate >= :startDate AND c.endDate <= :endDate")
    List<Contract> findByStartDateAndEndDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Contract c WHERE c.startDate >= :startDate")
    List<Contract> findByStartDate(@Param("startDate") LocalDate startDate);

    @Query("SELECT c FROM Contract c WHERE c.endDate <= :endDate")
    List<Contract> findByEndDate(@Param("endDate") LocalDate endDate);

    Page<Contract> findByEndDateLessThan(LocalDate date, Pageable pageable);

    Page<Contract> findByStartDateLessThanEqualAndEndDateGreaterThan(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Contract> findByStartDateGreaterThan(LocalDate date, Pageable pageable);

    @Query("SELECT c FROM Contract c ORDER BY c.id DESC")
    Page<Contract> findAllContractsOrderByIdDesc(Pageable pageable);

    @Query("SELECT c.ground FROM Contract c WHERE c.endDate <= :oneMonthFromNow AND c.ground.groundCategory != 'not ok'")
    List<Ground> findGroundsWithContractsEndingInOneMonth(@Param("oneMonthFromNow") LocalDate oneMonthFromNow);
}
