package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.RoomFacilities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomFacilitiesService {
    Page<RoomFacilities> getAllRoomFacilities(Pageable pageable);
    RoomFacilities getRoomFacilitiesById(Long id);
    void saveRoomFacilities(RoomFacilities roomFacilities);
    void deleteRoomFacilitiesById(Long id);
}
