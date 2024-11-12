package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c WHERE "
            + "(:startDate IS NULL OR c.startDate = :startDate)"
            + "AND (:endDate IS NULL OR c.endDate = :endDate)"
            + "AND (:taxCode IS NULL OR c.taxCode Like :taxCode)"
            + "AND (:nameCustomer IS NULL OR c.customer.name LIKE :nameCustomer)"

    )
    List<Contract> searchContract(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate,
                                  @Param("taxCode") String taxCode,
                                  @Param("nameCustomer") String nameCustomer);

    List<Contract> findByCustomer(Customer customer);

    List<Contract> findByStaff(Staff staff);

    @Query("SELECT c FROM Contract c WHERE c.startDate >= :startDate AND c.endDate <= :endDate")
    List<Contract> findByStartDateAndEndDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Contract c WHERE c.startDate >= :startDate")
    List<Contract> findByStartDate(@Param("startDate") LocalDate startDate);

    @Query("SELECT c FROM Contract c WHERE c.endDate <= :endDate")
    List<Contract> findByEndDate(@Param("endDate") LocalDate endDate);
}
