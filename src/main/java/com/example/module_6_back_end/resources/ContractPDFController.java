package com.example.module_6_back_end.resources;

import com.example.module_6_back_end.service.ContractPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractPDFController {
    private final ContractPdfService contractPdfService;

    public ContractPDFController(ContractPdfService contractPdfService) {
        this.contractPdfService = contractPdfService;
    }

    @GetMapping("/contracts/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        byte[] pdfContent = contractPdfService.generatePdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "contract.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}
