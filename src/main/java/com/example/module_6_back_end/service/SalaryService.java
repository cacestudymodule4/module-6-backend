package com.example.module_6_back_end.service;

import com.example.module_6_back_end.dto.PageRequestDTO;
import com.example.module_6_back_end.dto.SalaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SalaryService {
    Page<SalaryResponse> getSalary(PageRequestDTO pageRequest);

    List<SalaryResponse> getSalary();

}
