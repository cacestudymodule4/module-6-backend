package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Position;
import com.example.module_6_back_end.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    @Override
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public void deletePosition(Position position) {
        positionRepository.delete(position);
    }
}
