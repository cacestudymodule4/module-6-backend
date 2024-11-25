package com.example.module_6_back_end.dto;

import com.example.module_6_back_end.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryResponse {
    private String codeStaff;
    private String name;
    private Position position;
    private double salary;
}
