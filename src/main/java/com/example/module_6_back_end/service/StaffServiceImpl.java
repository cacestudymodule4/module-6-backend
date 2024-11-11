package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public boolean deleteStaff(Long id) {
        if (staffRepository.existsById(id)) {
            staffRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Staff> searchStaff(String keyword) {
        return staffRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        Optional<Staff> existingStaff = staffRepository.findById(id);
        if (existingStaff.isPresent()) {
            Staff updateStaff = existingStaff.get();
            updateStaff.setName(staff.getName());
            updateStaff.setAddress(staff.getAddress());
            updateStaff.setEmail(staff.getEmail());
            updateStaff.setPhone(staff.getPhone());
            updateStaff.setSalary(staff.getSalary());
            updateStaff.setStartDate(staff.getStartDate());
            return staffRepository.save(staff);
        } else {
            return null;
        }
    }

    @Override
    public Staff getStaffById(Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        return staff.orElse(null);
    }

}