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

    public ReportServiceImpl(ContractService contractService) {
        this.contractService = contractService;
    }

    @Override
    public Map<String, Double> getRevenue(ReportRequest reportRequest) {
        List<Contract> contracts = contractService.getContractsByStartDateAndEndDate(reportRequest);
        Map<String, Double> revenue = new LinkedHashMap<>();
        for (Contract contract : contracts) {
            if (revenue.containsKey(contract.getGround().getName())) {
                revenue.put(contract.getGround().getName(), revenue.get(contract.getGround().getName()) + contract.getTotalPrice());
            } else {
                revenue.put(contract.getGround().getName(), contract.getTotalPrice());
            }
        }
        return revenue;
    }
}
