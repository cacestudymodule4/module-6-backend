package com.example.module_6_back_end.service;

import com.example.module_6_back_end.model.Contract;
import com.example.module_6_back_end.repository.ContractRepository;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

@Service
public class ContractPdfService {
    private final TemplateEngine templateEngine;
    private final ContractRepository contractRepository;

    public ContractPdfService(TemplateEngine templateEngine, ContractRepository contractRepository) {
        this.templateEngine = templateEngine;
        this.contractRepository = contractRepository;
    }

    public byte[] generatePdf(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hợp đồng"));
        Context context = new Context();
        context.setVariable("contract", contract);
        String htmlContent = templateEngine.process("contract", context);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            String fontPath = Objects.requireNonNull(getClass().getResource("/fonts/arial.ttf")).getPath();
            renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo PDF", e);
        }
    }
}
