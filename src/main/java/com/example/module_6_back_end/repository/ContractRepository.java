package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Services;
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

    @Query("SELECT gs.services FROM Contract c " +
            "JOIN GroundService gs ON c.ground.id = gs.ground.id " +
            "WHERE c.customer.id = :customerId")
    List<Services> findServicesByCustomerId(@Param("customerId") Long customerId);
}
