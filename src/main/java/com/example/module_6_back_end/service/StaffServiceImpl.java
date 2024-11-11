package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepo;

    @Override
    public List<Staff> getAllStaff() {
       return staffRepo.findAll();
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepo.findById(id).orElse(null);
    }
}
