package com.example.module_6_back_end.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private boolean isDeleted;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @Override
    public String toString() {
        return "Staff{" +
                "codeStaff='" + codeStaff + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                ", position='" + position + '\'' +
                ", isDeleted=" + isDeleted +
                "} " + super.toString();
    }
}
