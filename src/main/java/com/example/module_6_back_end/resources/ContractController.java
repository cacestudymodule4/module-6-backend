package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.model.User;
import com.example.module_6_back_end.service.ContractService;
import com.example.module_6_back_end.service.EmailService;
import com.example.module_6_back_end.service.GroundService;
import com.example.module_6_back_end.service.UserService;
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
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public ContractController(ContractService contractService, GroundService groundService, UserService userService, EmailService emailService) {
        this.contractService = contractService;
        this.groundService = groundService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Contract>> list() {
        List<Contract> list = contractService.getContracts();
        LocalDate currentDay = LocalDate.now();
        for (Contract contract : list) {
            if (contract.getEndDate().isBefore(currentDay)) {
                contract.getGround().setStatus(false);
                groundService.setGround(contract.getGround());
            }
        }
        return ResponseEntity.ok().body(contractService.getContracts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Contract contract = contractService.getContractById(id);
        contract.getGround().setStatus(false);
        groundService.setGround(contract.getGround());
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
            @PageableDefault(size = 5)
            Pageable pageable) {
        User auTh = userService.getCurrentUser();
        Long id = auTh.getStaff().getId();
        LocalDate startDate = startDateStr != null && !startDateStr.isEmpty() ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = endDateStr != null && !endDateStr.isEmpty() ? LocalDate.parse(endDateStr) : null;
        if (auTh.getId() == 1) {
            return ResponseEntity.ok().body(contractService.searchAllContract(startDate, endDate, taxCode, nameCustomer, pageable));
        }
        return ResponseEntity.ok().body(contractService.searchContract(startDate, endDate, taxCode, nameCustomer, id, pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(
            @RequestBody Contract contract
    ) throws Exception {
        User auTh = userService.getCurrentUser();
        Staff staff = auTh.getStaff();
        contract.getGround().setStatus(true);
        groundService.setGround(contract.getGround());
        String codeTax = contractService.generateUniqueTaxCode();
        String codeContract = contractService.generateCode();
        contract.setStaff(staff);
        contract.setTaxCode(codeTax);
        contract.setCode(codeContract);
        System.out.println(contract);
        Contract contract1 = contractService.saveContract(contract);
        emailService.sendMail(contract1);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/save")
    public ResponseEntity<Void> save(
            @RequestBody Contract contract
    ) {
        Contract contractEdit = contractService.getContractById(contract.getId());
        contractEdit.setStaff(contract.getStaff());
        contractEdit.setCustomer(contract.getCustomer());
        contractEdit.setTerm(contract.getTerm());
        contractEdit.setStartDate(contract.getStartDate());
        contractEdit.setEndDate(contract.getEndDate());
        contractEdit.setDescription(contract.getDescription());
        contractService.saveContract(contractEdit);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Contract>> filterContracts(
            @RequestParam(required = false) String selectedFilter,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        User auTh = userService.getCurrentUser();
        Long id = auTh.getStaff().getId();
        Page<Contract> listFilter = contractService.filterContract(id, pageable, selectedFilter);
        return ResponseEntity.ok().body(listFilter);
    }

    @GetMapping("/findContract/{id}")
    public ResponseEntity<Contract> findContract(@PathVariable long id) {
        return ResponseEntity.ok().body(contractService.getContractById(id));
    }

    @GetMapping("/list-page")
    public ResponseEntity<Page<Contract>> listContracts(
            Pageable pageable) {
        User auTh = userService.getCurrentUser();
        Long id = auTh.getStaff().getId();
        if (auTh.getId() == 1) {
            return ResponseEntity.ok().body(contractService.getContractForAdmin(pageable));
        }
        return ResponseEntity.ok().body(contractService.getAllContracts(id, pageable));
    }

    @GetMapping("/list-rent")
    public ResponseEntity<List<Ground>> listAllOrExpiringSoon() {
        LocalDate oneMonthFromNow = LocalDate.now().plusMonths(1);
        List<Ground> grounds = contractService.getAddGroundH(oneMonthFromNow);
        return ResponseEntity.ok().body(new ArrayList<>(grounds));
    }

    @GetMapping("/checkDay")
    public ResponseEntity<LocalDate> checkContracts(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String groundCode

    ) {
        List<Contract> list = contractService.getContracts();
        for (Contract contract : list) {
            if (contract.getGround().getStatus().equals(status) && contract.getGround().getGroundCode().equals(groundCode)) {
                return ResponseEntity.ok().body(contract.getEndDate());
            }
        }
        return ResponseEntity.ok().body(LocalDate.now());
    }
}
