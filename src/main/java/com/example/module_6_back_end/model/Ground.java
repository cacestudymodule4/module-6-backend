package com.example.module_6_back_end.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ground {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Floor floor;
    private String groundCategory;
    private double area;
    private double price;
}
