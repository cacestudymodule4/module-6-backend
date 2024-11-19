package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.RoomFacilities;
import com.example.module_6_back_end.service.RoomFacilitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/a")
public class RoomFacilitiesController {
    private final RoomFacilitiesService roomFacilitiesService;

    @Autowired
    public RoomFacilitiesController(RoomFacilitiesService roomFacilitiesService) {
        this.roomFacilitiesService = roomFacilitiesService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<RoomFacilities>> getAllRoomFacilities(
            @PageableDefault(size = 1) Pageable pageable
    ) {
        System.out.println("okaf");
        return ResponseEntity.ok(roomFacilitiesService.getAllRoomFacilities(pageable));
    }

}
