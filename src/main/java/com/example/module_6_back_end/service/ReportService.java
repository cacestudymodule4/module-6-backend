package com.example.module_6_back_end.service;

import com.example.module_6_back_end.dto.ReportRequest;

import java.util.Map;

public interface ReportService {
    Map<String, Double> getRevenue(ReportRequest reportRequest);
}
