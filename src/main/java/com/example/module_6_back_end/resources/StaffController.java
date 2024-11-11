    package com.example.module_6_back_end.resources;

    import com.example.module_6_back_end.model.Staff;
    import com.example.module_6_back_end.service.StaffService;

    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.List;

    @RestController
    @RequestMapping("/api/staff")
    public class StaffController {
        private final StaffService staffService;

        public StaffController(StaffService staffService) {
            this.staffService = staffService;
        }

        @GetMapping
        public ResponseEntity<List<Staff>> getAllStaff() {
            List<Staff> staffList = staffService.getAllStaff();
            return ResponseEntity.ok(staffList);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
            boolean isDeleted = staffService.deleteStaff(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("/search")
        public ResponseEntity<List<Staff>> searchStaff(@RequestParam String keyword) {
            List<Staff> staffList = staffService.searchStaff(keyword);
            return ResponseEntity.ok(staffList);
        }

        @GetMapping("/{id}")
        public Staff getStaffById(@PathVariable Long id) {
            return staffService.getStaffById(id);
        }
    }