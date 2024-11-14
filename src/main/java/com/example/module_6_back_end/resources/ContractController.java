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
    public ResponseEntity<List<Contract>> searchContracts(
            @RequestParam(required = false) String taxCode,
            @RequestParam(required = false) String nameCustomer,
            @RequestParam(required = false) String startDateStr,
            @RequestParam(required = false) String endDateStr) {
        LocalDate startDate = startDateStr.isEmpty() ? null : LocalDate.parse(startDateStr);
        LocalDate endDate = endDateStr.isEmpty() ? null : LocalDate.parse(endDateStr);
        System.out.println(nameCustomer);
        List<Contract> contracts = contractService.searchContract(startDate, endDate, taxCode, nameCustomer);
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
    public ResponseEntity<List<Contract>> filterContracts(
            @RequestParam(required = false) String selectedFilter
    ) {
        if ("Có hiệu lực".equals(selectedFilter)) {
            return ResponseEntity.ok().body(contractService.getActiveContracts());
        } else if ("Hết hiệu lực".equals(selectedFilter)) {
            return ResponseEntity.ok().body(contractService.getExpiredContracts());
        } else if ("Chưa có hệu lực".equals(selectedFilter)) {
            return ResponseEntity.ok().body(contractService.getNotYetContract());
        } else {
            return ResponseEntity.ok().body(contractService.getContracts());
        }

    }

    @GetMapping("/findContract")
    public ResponseEntity<Contract> findContract(@RequestParam long id) {
        return ResponseEntity.ok().body(contractService.getContractById(id));
    }

}
