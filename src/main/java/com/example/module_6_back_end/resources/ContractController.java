package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.service.ContractService;
import com.example.module_6_back_end.service.GroundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/contract")
public class ContractController {
    private static final Logger log = LoggerFactory.getLogger(ContractController.class);
    private final ContractService contractService;
    private final GroundService groundService;

    @Autowired
    public ContractController(ContractService contractService, GroundService groundService) {
        this.contractService = contractService;
        this.groundService = groundService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Contract>> list() {
        List<Contract> list = contractService.getContracts();
        LocalDate currentDay = LocalDate.now();
        for (Contract contract : list) {
            if (contract.getEndDate().isBefore(currentDay)) {
                contract.getGround().setGroundCategory("ok");
                groundService.saveGround(contract.getGround());
            }
        }
        return ResponseEntity.ok().body(contractService.getContracts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Contract contract = contractService.getContractById(id);
        contract.getGround().setGroundCategory("ok");
        groundService.saveGround(contract.getGround());
        try {
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
            @PageableDefault(size = 1) Pageable pageable) {  // Nhận đối tượng Pageable cho phân trang
        LocalDate startDate = startDateStr != null && !startDateStr.isEmpty() ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = endDateStr != null && !endDateStr.isEmpty() ? LocalDate.parse(endDateStr) : null;
        Page<Contract> contracts = contractService.searchContract(startDate, endDate, taxCode, nameCustomer, pageable);
        return ResponseEntity.ok().body(contracts);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(
            @RequestBody Contract contract
    ) throws Exception {
        List<Contract> list = contractService.getContracts();
        boolean contractExists = false;
        for (Contract existingContract : list) {
            if (existingContract.getGround().getGroundCode().equals(contract.getGround().getGroundCode())) {
                contractExists = true;
                break;
            }
        }
        if (contractExists) {
            contract.getGround().setGroundCategory("not ok");
            groundService.saveGround(contract.getGround());
        }
        String codeTax = contractService.generateUniqueTaxCode();
        String codeContract = contractService.generateCode();
        contract.setTaxCode(codeTax);
        contract.setCode(codeContract);
        contractService.saveContract(contract);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/save")
    public ResponseEntity<Void> save(
            @RequestBody Contract contract
    ) {
        System.out.println("đã vào đc");
        Contract contractEdit = contractService.getContractById(contract.getId());
        contractEdit.setStaff(contract.getStaff());
        contractEdit.setCustomer(contract.getCustomer());
        contractEdit.setTerm(contract.getTerm());
        contractEdit.setStartDate(contract.getStartDate());
        contractEdit.setEndDate(contract.getEndDate());
        contractEdit.setDescription(contract.getDescription());
        contractService.saveContract(contractEdit);
        System.out.println(contract);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Contract>> filterContracts(
            @RequestParam(required = false) String selectedFilter,
            @PageableDefault(size = 5) Pageable pageable
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

    @GetMapping("/findContract/{id}")
    public ResponseEntity<Contract> findContract(@PathVariable long id) {
        return ResponseEntity.ok().body(contractService.getContractById(id));
    }

    @GetMapping("/list-page")
    public ResponseEntity<Page<Contract>> listContracts(@PageableDefault(size = 1) Pageable pageable) {
        return ResponseEntity.ok().body(contractService.getAllContracts(pageable));
    }

    @GetMapping("/list-rent")
    public ResponseEntity<List<Ground>> listAllOrExpiringSoon() {
        LocalDate oneMonthFromNow = LocalDate.now().plusMonths(1);
        List<Ground> grounds = contractService.getAddGroundH(oneMonthFromNow);
        List<Ground> groundsWithoutContract = groundService.findGroundNotInContract();
        Set<Ground> combinedGrounds = new HashSet<>();
        combinedGrounds.addAll(grounds);
        combinedGrounds.addAll(groundsWithoutContract);
        return ResponseEntity.ok().body(new ArrayList<>(combinedGrounds));
    }

    @GetMapping("/checkDay")
    public ResponseEntity<LocalDate> checkContracts(
            @RequestParam(required = false) String day
    ) {
        List<Contract> list = contractService.getContracts();
        for (Contract contract : list) {
            if (contract.getGround().getGroundCode().equals(day)) {
                return ResponseEntity.ok().body(contract.getEndDate());
            }
        }
        return ResponseEntity.ok().body(LocalDate.now());
    }
}
