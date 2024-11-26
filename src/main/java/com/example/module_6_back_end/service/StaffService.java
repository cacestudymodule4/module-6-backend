package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface StaffService {
    Page<Staff> getAllStaff(Pageable pageable);

    Page<Staff> searchStaff(String codeStaff, String name, String position, Pageable pageable);

    Page<Staff> findAllByIsDisabledFalse(Pageable pageable);

    Staff getStaffId(Long id);

    void disableStaff(Long staffId);

    Staff updateStaff(Long id, Staff staff);

    Staff getStaffById(Long id);

    Staff saveStaff(Staff staff);

    List<Staff> getStaffByNameContaining(String name);

    boolean existsByIdentification(String identification);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByCodeStaff(String codeStaff);

    List<Staff> getStaffList();
}
