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
    public List<Ground> findByGroundCategory(String groundCategory) {
        return groundRepository.findByGroundCategory(groundCategory);
    }

    @Override
    public List<Ground> findByGroundCodeContaining(String groundCode) {
        return groundRepository.findByGroundCodeContaining(groundCode);
    }

    @Override
    public List<Ground> findGroundNotInContract() {
        return groundRepository.findGroundsWithoutContract();
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
        System.out.println(ground);
        if (ground.getId() == null) {
            if (groundRepository.existsByGroundCode(ground.getGroundCode())) {
                throw new Exception("Mã mặt bằng đã tồn tại");
            }
        } else {
            Ground existingGround = groundRepository.findByGroundCode(ground.getGroundCode());
            if (existingGround != null && !existingGround.getId().equals(ground.getId())) {
                throw new Exception("Mã mặt bằng đã tồn tại");
            }
        }
        groundRepository.save(ground);
    }

    @Override
    public Page<Ground> searchGrounds(String groundCode, Double area, Double price, Pageable pageable) {
        return groundRepository.searchGround(groundCode, area, price, pageable);
    }

    @Override
    public void setGround(Ground ground) {
        groundRepository.save(ground);
    }
}
