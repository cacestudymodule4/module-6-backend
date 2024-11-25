package com.example.module_6_back_end.service;

import com.example.module_6_back_end.dto.ReportRequest;
import com.example.module_6_back_end.model.Contract;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    private final ContractService contractService;
    private final UserService userService;

    public ReportServiceImpl(ContractService contractService, UserService userService) {
        this.contractService = contractService;
        this.userService = userService;
    }

    @Override
    public Map<String, Double> getRevenue(ReportRequest reportRequest) {
        userService.isAdmin();
        List<Contract> contracts = contractService.getContractsByStartDateAndEndDate(reportRequest);
        Map<String, Double> revenue = new LinkedHashMap<>();
        for (Contract contract : contracts) {
            if (revenue.containsKey(contract.getGround().getGroundCode())) {
                revenue.put(contract.getGround().getGroundCode(), revenue.get(contract.getGround().getGroundCode()) + contract.getTotalPrice());
            } else {
                revenue.put(contract.getGround().getGroundCode(), contract.getTotalPrice());
            }
        }
        return revenue;
    }
}
