package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository iContractRepository;

    @Override
    public List<Contract> getContracts() {
        return iContractRepository.findAll();
    }

    @Override
    public Contract getContractById(long id) {
        return iContractRepository.findById(id).get();
    }

    @Override
    public List<Contract> searchContract(LocalDate startDate, LocalDate endDate, String taxCode, String nameCustomer) {
        return iContractRepository.searchContract(startDate, endDate, taxCode, nameCustomer);
    }

    @Override
    public void saveContract(Contract contract) {
        iContractRepository.save(contract);
    }

    @Override
    public void deleteContract(long id) {
        iContractRepository.deleteById(id);
    }

    public String generateUniqueTaxCode() {
        long texCode;
        String texCodeStr;
        boolean isUnique;
        List<Contract> list = getContracts();
        do {
            texCode = (long) (Math.random() * 1000000000);
            texCodeStr = String.valueOf(texCode);
            isUnique = true;
            for (Contract con : list) {
                if (con.getTaxCode().equals(texCodeStr)) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);
        return texCodeStr;
    }
}

