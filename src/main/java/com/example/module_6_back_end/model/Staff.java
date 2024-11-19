package com.example.module_6_back_end.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends Person {
    private String codeStaff;
    private double salary;
    private LocalDate startDate;
    private String position;

    @Override
    public String toString() {
        return "Staff{" +
                "codeStaff='" + codeStaff + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                ", position='" + position + '\'' +
                "} " + super.toString();
    }
}
