package com.example.module_6_back_end.service;

import com.example.module_6_back_end.exception.UnauthorizedException;
import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Role;
import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.model.User;
import com.example.module_6_back_end.repository.ContractRepository;
import com.example.module_6_back_end.repository.RoleRepository;
import com.example.module_6_back_end.repository.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final ContractService contractService;
    private final RegisterService registerService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ContractRepository contractRepository;

    public StaffServiceImpl(StaffRepository staffRepository, ContractService contractService, RegisterService registerService, UserService userService, RoleRepository roleRepository, ContractRepository contractRepository) {
        this.staffRepository = staffRepository;
        this.contractService = contractService;
        this.registerService = registerService;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public Page<Staff> getAllStaff(PageRequest pageRequest) {
        return staffRepository.findAll(pageRequest);
    }

    @Override
    public Staff getStaffId(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    @Override
    public String disableStaff(Long staffId) {
        // Tìm nhân viên
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhân viên"));

        // Kiểm tra hợp đồng còn hiệu lực
        List<Contract> contracts = contractRepository.findByStaff_Id(staffId);
        for (Contract contract : contracts) {
            if (contract.getEndDate() == null || contract.getEndDate().isAfter(LocalDate.now())) {
                throw new IllegalStateException("Nhân viên này đang có hợp đồng còn hiệu lực, không thể vô hiệu hóa.");
            }
        }

        // Đánh dấu vô hiệu hóa
        staff.setDisabled(true);
        staffRepository.save(staff);
        return "Nhân viên đã được vô hiệu hóa.";
    }

    @Override
    public List<Staff> getActiveStaff() {
        return staffRepository.findByIsDisabledFalse();
    }

    @Override
    public Page<Staff> searchStaff(String codeStaff, String name, String position, Pageable pageable) {
        return staffRepository.findByCodeStaffOrNameOrPosition(codeStaff, name, position, pageable);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        Optional<Staff> existingStaff = staffRepository.findById(id);
        if (existingStaff.isPresent()) {
            Staff staffToUpdate = existingStaff.get();
            staffToUpdate.setName(staff.getName());
            staffToUpdate.setEmail(staff.getEmail());
            staffToUpdate.setPhone(staff.getPhone());
            staffToUpdate.setAddress(staff.getAddress());
            staffToUpdate.setBirthday(staff.getBirthday());
            staffToUpdate.setSalary(staff.getSalary());
            staffToUpdate.setStartDate(staff.getStartDate());
            staffToUpdate.setPosition(staff.getPosition());
            return staffRepository.save(staffToUpdate);
        }
        return null;
    }

    @Override
    public Staff getStaffById(Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        return staff.orElse(null);
    }

    @Override
    public Staff saveStaff(Staff staff) {
        User auTh = userService.getCurrentUser();
        List<Role> roles = roleRepository.findByUser(auTh);
        boolean isAdmin = roles.stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedException("Bạn không có quyền thực hiện hành động này");
        }
        Staff newStaff = staffRepository.save(staff);
        registerService.registerUser(newStaff);
        return newStaff;
    }

    public boolean existsByEmail(String email) {
        return staffRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return staffRepository.existsByPhone(phone);
    }

    public boolean existsByCodeStaff(String codeStaff) {
        return staffRepository.existsByCodeStaff(codeStaff);
    }

    @Override
    public List<Staff> getStaffByNameContaining(String name) {
        return staffRepository.findByNameContaining(name);
    }

    @Override
    public List<Staff> getStaffList() {
        return staffRepository.findByCodeStaffNotContaining("STF001");
    }
}
