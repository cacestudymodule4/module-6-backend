package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Contract;

import java.time.LocalDate;
import java.util.List;

public interface ContractService {
    List<Contract> getContracts();

    Contract getContractById(long id);

    void saveContract(Contract contract);

    List<Contract> searchContract(LocalDate startDate, LocalDate endDate, String taxCode, String name);

    void deleteContract(long id);

    String generateUniqueTaxCode();
}
