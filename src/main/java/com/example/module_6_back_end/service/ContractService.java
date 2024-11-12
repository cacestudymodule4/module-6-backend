package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Staff;

import java.time.LocalDate;
import java.util.List;

public interface ContractService {
    List<Contract> getContracts();

    Contract getContractById(Long id);

    void saveContract(Contract contract);

    List<Contract> searchContract(LocalDate startDate, LocalDate endDate, String taxCode, String name);

    void deleteContract(Long id);

    String generateUniqueTaxCode();

    List<Contract> getContractsByCustomer(Customer customer);

    void deleteContracts(Customer customer);

    List<Contract> getContractsByStaff(Staff staff);

    void deleteContracts(Staff staff);
}
