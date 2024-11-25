package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Floor;
import com.example.module_6_back_end.model.FloorCategory;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.repository.FloorRepository;
import com.example.module_6_back_end.repository.GroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {
    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private GroundRepository groundRepository;

    @Override
    public Page<Floor> getAllFloors(Pageable pageable) {
        return floorRepository.findAll(pageable);
    }

    @Override
    public String deleteFloor(Long floorId) {
        Floor floor = floorRepository.findById(floorId).orElseThrow(() -> new RuntimeException("Tầng không tồn tại"));

        List<Ground> grounds = groundRepository.findByFloorIdAndStatusTrue(floorId);
        if (!grounds.isEmpty()) {
            return "Tầng này có mặt bằng đang được thuê, không thể xoá";
        }

        floor.setDeleted(true);
        floorRepository.save(floor);
        return "Tầng đã được xoá thành công";
    }

    @Override
    public void saveFloor(Floor floor) throws Exception {
        Floor existingFloor = floorRepository.findByFloorCode(floor.getFloorCode());

        if (floor.getId() == null) {
            if (existingFloor != null) {
                if (existingFloor.getDeleted()) {
                    throw new Exception("Mã tầng lầu đã bị xoá trước đó");
                }
                throw new Exception("Mã tầng lầu đã tồn tại");
            }

        }
        if (existingFloor != null && !existingFloor.getId().equals(floor.getId())) {
            if (existingFloor.getDeleted()) {
                throw new Exception("Mã tầng lầu đã bị xoá trước đó");
            }
            throw new Exception("Mã tầng lầu đã tồn tại");
        }
        floorRepository.save(floor);
    }

    @Override
    public Page<Floor> searchFloors(String name, Double areaFrom, Double areaTo, FloorCategory floorCategory, Pageable pageable) {
        return floorRepository.searchFloor(name, areaFrom, areaTo, floorCategory, pageable);
    }

    @Override
    public List<Floor> getFloors() {
        return floorRepository.findAll();
    }

    @Override
    public Page<Floor> findAllByDeletedFalse(Pageable pageable) {
        return floorRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Floor findFloorById(Long id) {
        return floorRepository.findById(id).orElse(null);
    }

    @Override
    public Floor findByFloorCode(String floorCode) {
        return floorRepository.findByFloorCode(floorCode);
    }

    @Override
    public void setFloor(Floor floor) {
        floorRepository.save(floor);
    }

    @Override
    public List<Floor> findAllByDeletedFalse() {
        return floorRepository.findAllByDeletedFalse();
    }
}
