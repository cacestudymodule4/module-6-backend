package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Staff;

import java.util.List;

public interface StaffService {
    List<Staff> getAllStaff();

    boolean deleteStaff(Long id);
}
