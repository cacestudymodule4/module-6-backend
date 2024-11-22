package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Ground;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroundService {
    List<Ground> getGrounds();

    Ground getGround(Long id);

    List<Ground> getGroundByStatus(Boolean status);

    List<Ground> findByGroundCodeContaining(String groundCode);

    List<Ground> findGroundNotInContract();

    Page<Ground> getAllGrounds(Pageable pageable);

    void deleteGround(Long id);

    void saveGround(Ground ground) throws Exception;

    Page<Ground> searchGrounds(String groundCode, Double area, Double price, Pageable pageable);

    void setGround(Ground ground);

    Page<Ground> findAllByDeletedFalse(Pageable pageable);

    Ground findGroundById(Long id);
}
