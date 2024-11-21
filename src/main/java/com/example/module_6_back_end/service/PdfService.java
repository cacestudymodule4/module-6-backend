package com.example.module_6_back_end.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PdfService {
    private final RestTemplate restTemplate;

    public PdfService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public byte[] getPdf(Long contractId) {
        String url = "http://localhost:8080/contracts/" + contractId + "/pdf";
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        return response.getBody();
    }
}
