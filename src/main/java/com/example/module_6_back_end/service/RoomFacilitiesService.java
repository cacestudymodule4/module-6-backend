package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.RoomFacilities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomFacilitiesService {
    Page<RoomFacilities> getAllRoomFacilities(Pageable pageable);

    RoomFacilities getRoomFacilitiesById(Long id);

    void saveRoomFacilities(RoomFacilities roomFacilities);

    void deleteRoomFacilitiesById(Long id);

    List<RoomFacilities> getListFacilities();

    Page<RoomFacilities> searchRoomFacilities(String facilitiesType, String facilitiesName, String ground, Pageable pageable);
}
