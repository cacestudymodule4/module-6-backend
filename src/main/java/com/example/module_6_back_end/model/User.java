package com.example.module_6_back_end.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true, nullable = false)
    private String phone;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private Boolean gender;
    @Column(unique = true, nullable = false)
    private String identification;
}
