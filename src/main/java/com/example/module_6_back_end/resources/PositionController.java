package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Position;
import com.example.module_6_back_end.service.PositionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public List<Position> getPositions() {
        return positionService.getPositions();
    }
}
