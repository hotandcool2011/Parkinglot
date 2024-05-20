package com.example.demo.service;

import com.example.demo.entity.ParkingHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    @Autowired
    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdfFromHtml(String htmlContent) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }

    public byte[] generatePdfFromThymeleafTemplate(String templateName, Context context) throws Exception {
        String htmlContent = templateEngine.process(templateName, context);
        return generatePdfFromHtml(htmlContent);
    }

    public void generateReceiptPdf(ParkingHistory parkingHistory) throws Exception {
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String entryReformat = parkingHistory.getTimeEntry().format(customFormatter);
        String exitReformat = parkingHistory.getTimeExit().format(customFormatter);
        Map<String, Object> data = new HashMap<>();
        data.put("totalAmount", parkingHistory.getParkingFee());
        data.put("plateNumber", parkingHistory.getPlateNumber());
        data.put("entryTime", entryReformat);
        data.put("exitTime", exitReformat);

        Context context = new Context();
        context.setVariables(data);

        byte[] pdfBytes = generatePdfFromThymeleafTemplate("receipt", context);
        String pdfFilePath = "receipt.pdf";
        File pdfFile = new File(pdfFilePath);
        try (FileOutputStream outputStream = new FileOutputStream(pdfFile)) {
            outputStream.write(pdfBytes);
        } catch (IOException e) {
            throw new Exception("Error saving PDF: " + e.getMessage());
        }
    }


}
