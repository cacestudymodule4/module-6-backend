package com.example.module_6_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String phone;
    private String email;
    private String identification;
    private Boolean gender;
    private LocalDate birthday;
    private String address;
}
