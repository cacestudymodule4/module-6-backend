package com.example.module_6_back_end.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ground {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Floor floor;
    private String groundCode;
    private Boolean status;
    private double area;
    private double price;
    private Boolean deleted = false;

    @ManyToOne
    private GroundCategory groundCategory;
}
