package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Staff;

import java.util.List;

public interface StaffService {
    List<Staff> getAllStaff();

    Staff getStaffId(Long id);

    void deleteStaff(Long id);

    List<Staff> searchStaff(String codeStaff, String name, String position);

    Staff updateStaff(Long id, Staff staff);

    Staff getStaffById(Long id);

    Staff saveStaff(Staff staff);

    List<Staff> findByNameContaining(String name);

    boolean existsByCodeStaff(String codeStaff);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
