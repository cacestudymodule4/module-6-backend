package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Floor;
import com.example.module_6_back_end.model.FloorCategory;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.service.FloorCategoryService;
import com.example.module_6_back_end.service.FloorService;
import com.example.module_6_back_end.service.GroundService;
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
    @Autowired
    private FloorService floorService;

    @Autowired
    private FloorCategoryService floorCategoryService;

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
        String result = floorService.deleteFloor(id);
        if (result.equals("Tầng đã được xoá thành công")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
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
            } else if (e.getMessage().equals("Mã tầng lầu đã bị xoá trước đó")) {
                return new ResponseEntity<>("Mã tầng lầu đã bị xoá trước đó", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFloor(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) Double areaFrom,
                                         @RequestParam(required = false) Double areaTo,
                                         @RequestParam(required = false) Long floorCategoryId,
                                         @PageableDefault(size = 5) Pageable pageable) {
        try {
            if (name.isEmpty()) {
                name = null;
            }
            FloorCategory floorCategory = floorCategoryService.findById(floorCategoryId);
            Page<Floor> floors = floorService.searchFloors(name, areaFrom, areaTo, floorCategory, pageable);
            return new ResponseEntity<>(floors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllFloors() {
        List<Floor> floors = floorService.findAllByDeletedFalse();
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

    @GetMapping("/roll-back")
    public ResponseEntity<?> rollBackFloor(@RequestParam(required = false) String floorCode) {
        Floor floor = floorService.findByFloorCode(floorCode);
        floor.setDeleted(false);
        floorService.setFloor(floor);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
