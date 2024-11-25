package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
