package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Services;
import com.example.module_6_back_end.service.GroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ground")
public class GroundController {
    @Autowired
    private GroundService service;

    @GetMapping("/list")
    public ResponseEntity<List<Ground>> list() {
        return ResponseEntity.ok().body(service.getGrounds());
    }

<<<<<<< HEAD
    @GetMapping("/list-rent")
    public ResponseEntity<List<Ground>> listNotRented() {
        return ResponseEntity.ok().body(service.findByGroundCategory("chưa thuê"));
    }

    @GetMapping("/findGround")
    public ResponseEntity<List<Ground>> findGround(
            @RequestParam String searchGround
    ) {
        return ResponseEntity.ok().body(service.findByNameContaining(searchGround));
    }
=======
>>>>>>> 2cda348c8dac14c917502e8b9104706a4aa0677f
}
