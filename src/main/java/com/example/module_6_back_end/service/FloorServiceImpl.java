package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Floor;
import com.example.module_6_back_end.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FloorServiceImpl implements FloorService {
    @Autowired
    private FloorRepository floorRepository;


    @Override
    public Page<Floor> getAllFloors(Pageable pageable) {
        return floorRepository.findAll(pageable);
    }

    @Override
    public void deleteFloor(Long id) {
        floorRepository.deleteById(id);
    }

    @Override
    public void saveFloor(Floor floor) throws Exception {
        if (floor.getId() == null) {
            if (floorRepository.existsByFloorCode(floor.getFloorCode())) {
                throw new Exception("Mã tầng lầu đã tồn tại");
            }
        } else {
            Floor existingFloor = floorRepository.findByFloorCode(floor.getFloorCode());
            if (existingFloor != null && !existingFloor.getId().equals(floor.getId())) {
                throw new Exception("Mã tầng lầu đã tồn tại");
            }
        }
        floorRepository.save(floor);
    }

    @Override
    public Page<Floor> searchFloors(String name, Double area, String typeOfFloor, Pageable pageable) {
        return floorRepository.searchFloor(name, area, typeOfFloor, pageable);
    }

}
