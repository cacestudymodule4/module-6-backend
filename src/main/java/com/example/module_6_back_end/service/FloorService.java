package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Floor;
import com.example.module_6_back_end.model.FloorCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FloorService {
    Page<Floor> getAllFloors(Pageable pageable);

    String deleteFloor(Long id);

    void saveFloor(Floor floor) throws Exception;

    Page<Floor> searchFloors(String name, Double areaFrom, Double areaTo, FloorCategory floorCategory, Pageable pageable);

    List<Floor> getFloors();

    Page<Floor> findAllByDeletedFalse(Pageable pageable);

    Floor findFloorById(Long id);

    Floor findByFloorCode(String floorCode);

    void setFloor(Floor floor);

    List<Floor> findAllByDeletedFalse();
}
