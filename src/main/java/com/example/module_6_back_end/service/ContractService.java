package com.example.module_6_back_end.service;

import com.example.module_6_back_end.dto.ReportRequest;
import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.model.Customer;
import com.example.module_6_back_end.model.Ground;
import com.example.module_6_back_end.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ContractService {
    List<Contract> getContracts();

    Contract getContractById(Long id);

    Contract saveContract(Contract contract);

    Page<Contract> searchContract(LocalDate startDate, LocalDate endDate, String taxCode, String name,Long id,Pageable pageable);

    Page<Contract> searchAllContract(LocalDate startDate, LocalDate endDate, String taxCode, String name,Pageable pageable);

    void deleteContract(Long id);

    String generateUniqueTaxCode();

    String generateCode();

    List<Contract> getContractsByCustomer(Customer customer);

    void deleteContracts(Customer customer);

    List<Contract> getContractsByStaff(Staff staff);

    void deleteContracts(Staff staff);

    List<Contract> getContractsByStartDateAndEndDate(ReportRequest reportRequest);

    Page<Contract> getAllContracts(Long id,Pageable pageable);

    Page<Contract> getContractForAdmin(Pageable pageable);

    List<Ground> getAddGroundH( LocalDate oneMonthFromNow);

    Page<Contract> filterContract(Long id,Pageable pageable,String filter);

}
