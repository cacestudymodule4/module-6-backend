package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.dto.ContractDTO;
import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.service.ContractService;
import com.example.module_6_back_end.service.CustomerService;
import com.example.module_6_back_end.service.GroundService;
import com.example.module_6_back_end.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contract")
public class ContractController {
    private static final Logger log = LoggerFactory.getLogger(ContractController.class);
    private final ContractService contractService;
    private final StaffService staffService;
    private final GroundService groundService;
    private final CustomerService customerService;

    @Autowired
    public ContractController(ContractService contractService, StaffService staffService, GroundService groundService, CustomerService customerService) {
        this.contractService = contractService;
        this.staffService = staffService;
        this.groundService = groundService;
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Contract>> list() {
        return ResponseEntity.ok().body(contractService.getContracts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        try {
            Contract contract = contractService.getContractById(id);
            contract.getGround().setGroundCategory("chưa thuê");
            groundService.saveGround(contract.getGround());
            contractService.deleteContract(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Contract>> searchContracts(
            @RequestParam(required = false) String taxCode,
            @RequestParam(required = false) String nameCustomer,
            @RequestParam(required = false) String startDateStr,
            @RequestParam(required = false) String endDateStr,
            Pageable pageable) {  // Nhận đối tượng Pageable cho phân trang

        // Chuyển đổi các tham số ngày tháng từ String sang LocalDate
        LocalDate startDate = startDateStr != null && !startDateStr.isEmpty() ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = endDateStr != null && !endDateStr.isEmpty() ? LocalDate.parse(endDateStr) : null;

        // Gọi service để tìm kiếm hợp đồng
        Page<Contract> contracts = contractService.searchContract(startDate, endDate, taxCode, nameCustomer, pageable);

        // Trả về kết quả dưới dạng ResponseEntity
        return ResponseEntity.ok().body(contracts);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(
            @RequestBody Contract contract
    ) {
        String codeTax = contractService.generateUniqueTaxCode();
        String codeContract = contractService.generateCode();
        contract.setTaxCode(codeTax);
        contract.setCode(codeContract);
        contract.getGround().setGroundCategory("Đã thuê");
        groundService.saveGround(contract.getGround());
        System.out.println(contract.getGround().getGroundCategory());
        System.out.println(contract);
        contractService.saveContract(contract);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/save")
    public ResponseEntity<Void> save(
            @RequestBody ContractDTO contractDto
    ) {
        System.out.println(contractDto.getId());
        Ground ground = groundService.getGround(contractDto.getGround());
        Staff staff = staffService.getStaffById(contractDto.getStaffId());
        double totalPrice = ground.getPrice() * contractDto.getTerm();
        Contract contract = contractService.getContractById(contractDto.getId());
        contract.setDescription(contractDto.getContent());
        contract.setStartDate(contractDto.getStartDay());
        contract.setEndDate(contractDto.getEndDay());
        contract.setTerm(contractDto.getTerm());
        contract.setStaff(staff);
        contract.setGround(ground);
        contract.setTotalPrice(totalPrice);
        contractService.saveContract(contract);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Contract>> filterContracts(
            @RequestParam(required = false) String selectedFilter,
            Pageable pageable  // Spring sẽ tự động xử lý tham số phân trang từ query parameters
    ) {
        if ("Có hiệu lực".equals(selectedFilter)) {
            return ResponseEntity.ok().body(contractService.getActiveContracts(pageable));
        } else if ("Hết hiệu lực".equals(selectedFilter)) {
            return ResponseEntity.ok().body(contractService.getExpiredContracts(pageable));
        } else if ("Chưa có hiệu lực".equals(selectedFilter)) {
            return ResponseEntity.ok().body(contractService.getNotYetContract(pageable));
        } else {
            return ResponseEntity.ok().body(contractService.getAllContracts(pageable));
        }
    }

    @GetMapping("/findContract")
    public ResponseEntity<Contract> findContract(@RequestParam long id) {
        return ResponseEntity.ok().body(contractService.getContractById(id));
    }

    @GetMapping("/list-1")
    public ResponseEntity<Page<Contract>> listContracts(
            @RequestParam("page") int page, @RequestParam("size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(contractService.getAllContracts(pageable));
    }

}
