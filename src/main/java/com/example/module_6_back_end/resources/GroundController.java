package com.example.module_6_back_end.resources;

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
        return ResponseEntity.ok().body(groundService.getByGroundCodeContaining(searchGround));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<Ground> grounds = groundService.findAllByDeletedFalse(pageable);
        if (grounds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(grounds);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGround(@PathVariable Long id) {
        try {
            Ground ground = groundService.findGroundById(id);
            if (ground.getStatus()) {
                return new ResponseEntity<>("Mặt bằng đang thuê, không được phép xoá", HttpStatus.BAD_REQUEST);
            } else {
                ground.setDeleted(true);
                groundService.setGround(ground);
                return new ResponseEntity<>(HttpStatus.OK);
            }
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
            if (e.getMessage().equals("Mã mặt bằng đã tồn tại.")) {
                return new ResponseEntity<>("Mã mặt bằng đã tồn tại.", HttpStatus.BAD_REQUEST);
            } else if (e.getMessage().equals("Mã mặt bằng đã bị xoá trước đó")) {
                return new ResponseEntity<>("Mã mặt bằng đã bị xoá trước đó", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFloor(@RequestParam(required = false) String groundCode,
                                         @RequestParam(required = false) Double areaFrom,
                                         @RequestParam(required = false) Double areaTo,
                                         @RequestParam(required = false) Double priceFrom,
                                         @RequestParam(required = false) Double priceTo,
                                         @PageableDefault(size = 5) Pageable pageable) {
        try {
            if (groundCode.isEmpty()) {
                groundCode = null;
            }
            Page<Ground> grounds = groundService.searchGrounds(groundCode, areaFrom, areaTo, priceFrom, priceTo, pageable);
            return new ResponseEntity<>(grounds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find-gr/{id}")
    public ResponseEntity<?> getGround(@PathVariable Long id) {
        return ResponseEntity.ok().body(groundService.getGround(id));
    }

    @GetMapping("/roll-back")
    public ResponseEntity<?> rollBackGround(@RequestParam(required = false) String groundCode) {
        Ground ground = groundService.findByGroundCode(groundCode);
        ground.setDeleted(false);
        groundService.setGround(ground);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
