package com.example.module_6_back_end.service;

import com.example.module_6_back_end.exception.ValidationException;
import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.repository.ContractRepository;
import com.example.module_6_back_end.repository.PositionRepository;
import com.example.module_6_back_end.repository.RoleRepository;
import com.example.module_6_back_end.repository.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final ContractService contractService;
    private final RegisterService registerService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ContractRepository contractRepository;
    private final PositionRepository positionRepository;

    public StaffServiceImpl(StaffRepository staffRepository, ContractService contractService, RegisterService registerService, UserService userService, RoleRepository roleRepository, ContractRepository contractRepository, PositionRepository positionRepository) {
        this.staffRepository = staffRepository;
        this.contractService = contractService;
        this.registerService = registerService;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.contractRepository = contractRepository;
        this.positionRepository = positionRepository;
    }

    public Page<Staff> getAllStaff(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }

    @Override
    public Page<Staff> searchStaff(String codeStaff, String name, String position, Pageable pageable) {
        return staffRepository.findByCodeStaffOrNameOrPosition(codeStaff, name, position, pageable);
    }

    @Override
    public Page<Staff> findAllByIsDisabledFalse(Pageable pageable) {
        return staffRepository.findAllByIsDisabledFalse(pageable);
    }

    @Override
    public Staff getStaffId(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    @Override
    public void disableStaff(Long staffId) {
        userService.isAdmin();
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhân viên"));
        staff.setDisabled(true);
        staffRepository.save(staff);
        System.out.println(staff);
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
            staffToUpdate.setIdentification(staff.getIdentification());
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
        userService.isAdmin();
        Map<String, String> error = new HashMap<>();

        if (staffRepository.existsByCodeStaff(staff.getCodeStaff())) {
            error.put("codeStaff", "Mã nhân viên đã tồn tại");
        }

        Staff existingStaff = staffRepository.findByIdentification(staff.getIdentification());
        if (existingStaff != null) {
            if (existingStaff.isDisabled()) {
                existingStaff.setDisabled(false);
                existingStaff.setEmail(existingStaff.getEmail());
                existingStaff.setPhone(existingStaff.getPhone());
                return staffRepository.save(existingStaff);
            } else {
                error.put("CCCD/CMND", "CCCD/CMND đã tồn tại và đang hoạt động.");
            }
        }

        if (staffRepository.existsByEmail(staff.getEmail())) {
            error.put("Email", "Email đã có người sử dụng");
        }
        if (staffRepository.existsByPhone(staff.getPhone())) {
            error.put("Phone", "SĐT đã có người sử dụng");
        }

        if (!error.isEmpty()) {
            throw new ValidationException(error);
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

    @Override
    public boolean existsByCodeStaff(String codeStaff) {
        return staffRepository.existsByCodeStaff(codeStaff);
    }

    @Override
    public boolean existsByIdentification(String identification) {
        return staffRepository.existsByIdentification(identification);
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
