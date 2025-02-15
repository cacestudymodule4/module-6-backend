package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.exception.UnauthorizedException;
import com.example.module_6_back_end.exception.ValidationException;
import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.repository.StaffRepository;
import com.example.module_6_back_end.service.StaffService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffService staffService;
    private final StaffRepository staffRepository;

    public StaffController(StaffService staffService, StaffRepository staffRepository) {
        this.staffService = staffService;
        this.staffRepository = staffRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllStaff(@PageableDefault(size = 5) Pageable pageable) {
        Page<Staff> staffPage = staffRepository.findAllByIsDisabledFalse(pageable);
        if (staffPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(staffPage, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Staff>> searchStaff(@RequestParam(required = false) String codeStaff,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String position,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Staff> staffPage = staffService.searchStaff(codeStaff, name, position, pageable);
        return ResponseEntity.ok(staffPage);
    }

    @GetMapping("/list-add")
    public ResponseEntity<List<Staff>> getAllStaffList() {
        return ResponseEntity.ok().body(staffService.getStaffList());
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disableStaff(@PathVariable Long id) {
        try {
            staffService.disableStaff(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStaff(@RequestBody Staff staff) {
        try {
            Staff saveStaff = staffService.saveStaff(staff);
            if (!saveStaff.isDisabled()) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Nhân viên đã được kích hoạt lại");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(saveStaff);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình thêm nhân viên");
        }
    }

    @GetMapping("/{id}")
    public Staff getStaffById(@PathVariable Long id) {
        return staffService.getStaffById(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editStaff(@PathVariable Long id, @RequestBody Staff staff) {
        try {
            Staff updatedStaff = staffService.updateStaff(id, staff);
            if (updatedStaff != null) {
                return ResponseEntity.ok(updatedStaff);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nhân viên không tồn tại");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi cập nhật nhân viên");
        }
    }

    @GetMapping("/findStaff")
    public ResponseEntity<List<Staff>> findStaff(@RequestParam String searchStaff) {
        return ResponseEntity.ok().body(staffService.getStaffByNameContaining(searchStaff));
    }
}
