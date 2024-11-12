package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final ContractService contractService;

    public StaffServiceImpl(StaffRepository staffRepository, ContractService contractService) {
        this.staffRepository = staffRepository;
        this.contractService = contractService;
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffId(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteStaff(Long id) {
        Staff staff = getStaffId(id);
        if (staff == null) {
            throw new IllegalArgumentException("Không tìm thấy nhân viên!!!");
        }
        contractService.deleteContracts(staff);
        staffRepository.deleteById(id);
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

    @Override
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }
}
