package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReportController {
    private final ContractService contractService;

    public ReportController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/api/report")
    public ResponseEntity<Map<String, Double>> report() {
        List<Contract> contracts = contractService.getContracts();
        Map<String, Double> revenue = new LinkedHashMap<>();
        for (Contract contract : contracts) {
            if (revenue.containsKey(contract.getGround().getName())) {
                revenue.put(contract.getGround().getName(), revenue.get(contract.getGround().getName()) + contract.getTotalPrice());
            } else {
                revenue.put(contract.getGround().getName(), contract.getTotalPrice());
            }
        }
        return ResponseEntity.ok().body(revenue);
    }
}
