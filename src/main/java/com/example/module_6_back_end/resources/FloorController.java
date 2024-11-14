package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Floor;
import com.example.module_6_back_end.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/floor")
public class FloorController {
    @Autowired
    private FloorService floorService;

    @GetMapping("/list")
    public ResponseEntity<?> getListFloor(@PageableDefault(size = 5) Pageable pageable) {
        Page<Floor> floors = floorService.getAllFloors(pageable);
        if (floors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(floors, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFloor(@PathVariable Long id) {
        try {
            floorService.deleteFloor(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveFloor(@RequestBody Floor floor) {
        try {
            floorService.saveFloor(floor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equals("Mã tầng lầu đã tồn tại")) {
                return new ResponseEntity<>("Mã tầng lầu đã tồn tại", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFloor(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) Double area,
                                         @RequestParam(required = false) String typeOfFloor,
                                         @PageableDefault(size = 5) Pageable pageable) {
        try {
            if (name.isEmpty()) {
                name = null;
            } else if (typeOfFloor.isEmpty()) {
                typeOfFloor = null;
            }
            Page<Floor> floors = floorService.searchFloors(name, area, typeOfFloor, pageable);
            return new ResponseEntity<>(floors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
