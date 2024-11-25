package com.example.module_6_back_end.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String floorCode;
    private String name;
    private Double area;
    private Long capacity;
    @ManyToOne
    private Building building;
    private Boolean deleted = false;
    @ManyToOne
    private FloorCategory floorCategory;
}
