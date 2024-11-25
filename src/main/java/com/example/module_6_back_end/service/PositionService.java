package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Position;

import java.util.List;

public interface PositionService {
    List<Position> getPositions();

    Position savePosition(Position position);

    void deletePosition(Position position);
}
