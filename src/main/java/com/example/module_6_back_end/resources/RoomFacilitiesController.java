package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.RoomFacilities;
import com.example.module_6_back_end.service.RoomFacilitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
public class RoomFacilitiesController {
    private static final Logger log = LoggerFactory.getLogger(RoomFacilitiesController.class);
    private final RoomFacilitiesService roomFacilitiesService;

    @Autowired
    public RoomFacilitiesController(RoomFacilitiesService roomFacilitiesService) {
        this.roomFacilitiesService = roomFacilitiesService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<RoomFacilities>> getAllRoomFacilities(
            @PageableDefault(size = 1) Pageable pageable
    ) {
        return ResponseEntity.ok(roomFacilitiesService.getAllRoomFacilities(pageable));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoomFacilities(@PathVariable Long id) {
        roomFacilitiesService.deleteRoomFacilitiesById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RoomFacilities>> searchRoomFacilities(
            @RequestParam(required = false) String facilitiesType,
            @RequestParam(required = false) String facilitiesName,
            @RequestParam(required = false) String ground,
            @PageableDefault(size = 1) Pageable pageable
    ) {
        Page<RoomFacilities> list = roomFacilitiesService.searchRoomFacilities(facilitiesType, facilitiesName, ground, pageable);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoomFacilities(@RequestBody RoomFacilities roomFacilities) {
        List<RoomFacilities> list = roomFacilitiesService.getListFacilities();
        for (RoomFacilities facilities : list) {
            if (facilities.getGround().getId().equals(roomFacilities.getGround().getId())
                    && facilities.getName().equals(roomFacilities.getName())) {
                facilities.setQuantity(facilities.getQuantity() + roomFacilities.getQuantity());
                roomFacilitiesService.saveRoomFacilities(facilities);
                return ResponseEntity.ok().build();
            }
        }
        roomFacilitiesService.saveRoomFacilities(roomFacilities);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-facilities/{id}")
    public ResponseEntity<RoomFacilities> findFacilities(@PathVariable long id) {
        System.out.println(roomFacilitiesService.getRoomFacilitiesById(id));
        return ResponseEntity.ok().body(roomFacilitiesService.getRoomFacilitiesById(id));
    }

    @PutMapping("/save")
    public ResponseEntity<?> updateRoomFacilities(@RequestBody RoomFacilities roomFacilities) {
        roomFacilitiesService.saveRoomFacilities(roomFacilities);
        System.out.println(roomFacilities);
        return ResponseEntity.ok().build();
    }
}
