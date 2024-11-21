package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Floor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FloorService {
    Page<Floor> getAllFloors(Pageable pageable);

    void deleteFloor(Long id);

    void saveFloor(Floor floor) throws Exception;

    Page<Floor> searchFloors(String name, Double area, String typeOfFloor, Pageable pageable);

    List<Floor> getFloors();
}
