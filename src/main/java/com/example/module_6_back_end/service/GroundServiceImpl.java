package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.repository.GroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroundServiceImpl implements GroundService {
    @Autowired
    private GroundRepository groundRepository;

    @Override
    public List<Ground> getGrounds() {
        return groundRepository.findAll();
    }

    @Override
    public Ground getGround(Long id) {
        return groundRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ground> getGroundByStatus(Boolean status) {
        return groundRepository.findByStatus(status);
    }

    @Override
    public List<Ground> getByGroundCodeContaining(String groundCode) {
        return groundRepository.findByGroundCodeContaining(groundCode);
    }

    @Override
    public List<Ground> getGroundNotInContract() {
        return groundRepository.findGroundsWithoutTwoOrMoreContracts();
    }

    @Override
    public Page<Ground> getAllGrounds(Pageable pageable) {
        return groundRepository.findAll(pageable);
    }

    @Override
    public void deleteGround(Long id) {
        groundRepository.deleteById(id);
    }

    @Override
    public void saveGround(Ground ground) throws Exception {
        Ground existingGround = groundRepository.findByGroundCode(ground.getGroundCode());

        if (ground.getId() == null) {
            if (existingGround != null) {
                if (existingGround.getDeleted()) {
                    throw new Exception("Mã mặt bằng đã bị xoá trước đó");
                }
                throw new Exception("Mã mặt bằng đã tồn tại.");
            }
        }

        if (existingGround != null && !existingGround.getId().equals(ground.getId())) {
            if (existingGround.getDeleted()) {
                throw new Exception("Mã mặt bằng đã bị xoá trước đó");
            }
            throw new Exception("Mã mặt bằng đã tồn tại.");
        }
        groundRepository.save(ground);
    }


    @Override
    public Page<Ground> searchGrounds(String groundCode, Double areaFrom, Double areaTo, Double priceFrom, Double priceTo, Pageable pageable) {
        return groundRepository.searchGrounds(groundCode, areaFrom, areaTo, priceFrom, priceTo, pageable);
    }

    @Override
    public void setGround(Ground ground) {
        groundRepository.save(ground);
    }

    @Override
    public Page<Ground> findAllByDeletedFalse(Pageable pageable) {
        return groundRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Ground findGroundById(Long id) {
        return groundRepository.findById(id).orElse(null);
    }

    @Override
    public Ground findByGroundCode(String groundCode) {
        return groundRepository.findByGroundCode(groundCode);
    }

    @Override
    public List<Ground> findByFloorIdAndStatusTrue(Long floorId) {
        return groundRepository.findByFloorIdAndStatusTrue(floorId);
    }
}
