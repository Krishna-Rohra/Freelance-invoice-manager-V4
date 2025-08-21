package com.krishna.freelance.controller;

import com.krishna.freelance.model.ServiceEntity;
import com.krishna.freelance.service.ServiceService;
import com.krishna.freelance.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin
public class ServiceController {
    private final ServiceService serviceService;
    private final PdfService pdfService;

    public ServiceController(ServiceService serviceService, PdfService pdfService) {
        this.serviceService = serviceService;
        this.pdfService = pdfService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ServiceEntity dto) {
        try {
            ServiceEntity saved = serviceService.create(dto);
            return ResponseEntity.created(URI.create("/api/services/" + saved.getId())).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to create service");
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> all() {
        return ResponseEntity.ok(serviceService.getAll());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ServiceEntity>> byClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(serviceService.getByClient(clientId));
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        try {
            List<ServiceEntity> services = serviceService.getAll();
            byte[] pdfBytes = pdfService.generateInvoicePdf(services);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice.pdf");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}