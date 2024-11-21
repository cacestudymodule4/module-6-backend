package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Floor;
import com.example.module_6_back_end.service.FloorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floor")
public class FloorController {
    private static final Logger log = LoggerFactory.getLogger(FloorController.class);
    @Autowired
    private FloorService floorService;

    @GetMapping("/list")
    public ResponseEntity<?> getListFloor(@PageableDefault(size = 5) Pageable pageable) {
        Page<Floor> floors = floorService.findAllByDeletedFalse(pageable);
        if (floors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(floors, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFloor(@PathVariable Long id) {
        try {
            Floor floor = floorService.findFloorById(id);
            floor.setDeleted(true);
            floorService.saveFloor(floor);
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

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllFloors() {
        List<Floor> floors = floorService.getFloors();
        if (floors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(floors, HttpStatus.OK);
    }

    @GetMapping("/get-list")
    public ResponseEntity<List<Floor>> getListSearch(){
        List<Floor> floors = floorService.getFloors();
        System.out.println(floors);
        return ResponseEntity.ok().body(floors);
    }
}
