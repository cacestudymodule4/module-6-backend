package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customer c " +
            "JOIN person p ON c.id = p.id " +
            "ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) COLLATE utf8mb4_vietnamese_ci ASC",
            countQuery = "SELECT COUNT(*) FROM customer c " +
                    "JOIN person p ON c.id = p.id",
            nativeQuery = true)
    Page<Customer> findAllCustomersSorted(Pageable pageable);

    boolean existsByEmail(String email);

    Customer findCustomerByIdentification(String identification);

    boolean existsByPhone(String phone);

    List<Customer> findByNameContaining(String name);

    Page<Customer> findByNameContaining(String name, Pageable pageable);

    Page<Customer> findByIdentificationContaining(String identification, Pageable pageable);

    @Query(value = "SELECT * FROM customer c " +
            "JOIN person p ON c.id = p.id " +
            "WHERE p.name LIKE %:name% AND p.identification LIKE %:identification% " +
            "ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) COLLATE utf8mb4_vietnamese_ci ASC",
            countQuery = "SELECT COUNT(*) FROM customer c " +
                    "JOIN person p ON c.id = p.id " +
                    "WHERE p.name LIKE %:name% AND p.identification LIKE %:identification%",
            nativeQuery = true)
    Page<Customer> findByNameAndIdentification(@Param("name") String name,
                                               @Param("identification") String identification,
                                               Pageable pageable);

    @Query("SELECT c FROM Customer c ORDER BY c.id DESC")
    List<Customer> findAllCustomersSortedByIdDesc();
}

