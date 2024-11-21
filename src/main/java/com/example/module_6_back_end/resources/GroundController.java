package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Floor;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.service.GroundService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ground")
public class GroundController {
    private final GroundService groundService;

    public GroundController(GroundService groundService) {
        this.groundService = groundService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Ground>> list() {
        return ResponseEntity.ok().body(groundService.getGrounds());
    }

    @GetMapping("/list-rent")
    public ResponseEntity<List<Ground>> listNotRented() {
        return ResponseEntity.ok().body(groundService.getGroundByStatus(false));
    }

    @GetMapping("/findGround")
    public ResponseEntity<List<Ground>> findGround(
            @RequestParam String searchGround
    ) {
        return ResponseEntity.ok().body(groundService.findByGroundCodeContaining(searchGround));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<Ground> grounds = groundService.getAllGrounds(pageable);
        if (grounds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(grounds);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGround(@PathVariable Long id) {
        try {
            groundService.deleteGround(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveGround(@RequestBody Ground ground) {
        try {
            groundService.saveGround(ground);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equals("Mã mặt bằng đã tồn tại")) {
                return new ResponseEntity<>("Mã mặt bằng đã tồn tại", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFloor(@RequestParam(required = false) String groundCode,
                                         @RequestParam(required = false) Double area,
                                         @RequestParam(required = false) Double price,
                                         @PageableDefault(size = 5) Pageable pageable) {
        try {
            if (groundCode.isEmpty()) {
                groundCode = null;
            }
            Page<Ground> grounds = groundService.searchGrounds(groundCode, area, price, pageable);
            return new ResponseEntity<>(grounds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
