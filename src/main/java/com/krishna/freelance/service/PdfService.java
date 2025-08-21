package com.krishna.freelance.service;

import com.krishna.freelance.model.ServiceEntity;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateInvoicePdf(List<ServiceEntity> services) throws DocumentException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Freelance Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Spacing

            // Add date
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph date = new Paragraph("Generated on: " + 
                java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), dateFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);
            document.add(new Paragraph(" ")); // Spacing

            // Create table
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            // Add headers
            String[] headers = {"Client", "Description", "Date", "Hours", "Rate (₹)", "Total (₹)"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(new java.awt.Color(211, 211, 211)); // Light gray
                table.addCell(cell);
            }

            // Add data rows
            double grandTotal = 0.0;
            for (ServiceEntity service : services) {
                double total = service.getHours() * service.getRate();
                grandTotal += total;

                table.addCell(new Phrase(service.getClient().getName()));
                table.addCell(new Phrase(service.getDescription()));
                table.addCell(new Phrase(service.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                table.addCell(new Phrase(String.format("%.1f", service.getHours())));
                table.addCell(new Phrase(String.format("%.2f", service.getRate())));
                table.addCell(new Phrase(String.format("%.2f", total)));
            }

            document.add(table);
            document.add(new Paragraph(" ")); // Spacing

            // Add grand total
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph grandTotalPara = new Paragraph("Grand Total: ₹" + String.format("%.2f", grandTotal), totalFont);
            grandTotalPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(grandTotalPara);

        } finally {
            document.close();
        }

        return baos.toByteArray();
    }
}
