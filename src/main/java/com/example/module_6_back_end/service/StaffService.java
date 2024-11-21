package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {
    Page<Staff> getAllStaff(PageRequest pageRequest);

    Staff getStaffId(Long id);

    void deleteStaff(Long id);

    Page<Staff> searchStaff(String codeStaff, String name, String position, Pageable pageable);

    Staff updateStaff(Long id, Staff staff);

    Staff getStaffById(Long id);

    Staff saveStaff(Staff staff);

    List<Staff> getStaffByNameContaining(String name);

    boolean existsByCodeStaff(String codeStaff);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<Staff> getStaffList();
}
