package com.krishna.freelance.controller;

import com.krishna.freelance.model.Client;
import com.krishna.freelance.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Client client) {
        try {
            Client saved = clientService.create(client);
            return ResponseEntity.created(URI.create("/api/clients/" + saved.getId())).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to create client");
        }
    }

    @GetMapping
    public ResponseEntity<List<Client>> all() {
        return ResponseEntity.ok(clientService.getAll());
    }
}