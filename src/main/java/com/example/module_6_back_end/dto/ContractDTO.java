package com.example.module_6_back_end.dto;

import java.time.LocalDate;

import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Staff;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long id;
    private String code;
    private String taxCode;
    private double totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private double deposit;
    private Long term;
    private Staff staff;
    private Ground ground;
    private Customer customer;
}
