package com.example.module_6_back_end.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long id;
    private String cmd;
    private Long staffId;
    private Long term;
    private Long ground;
    private LocalDate startDay;
    private LocalDate endDay;
    private double price;
    private double deposit;
    private String content;

}
