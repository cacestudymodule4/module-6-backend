package com.example.module_6_back_end.model;

import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne
    private Position position;
}
