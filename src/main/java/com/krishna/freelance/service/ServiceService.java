package com.krishna.freelance.service;

import com.krishna.freelance.model.Client;
import com.krishna.freelance.model.ServiceEntity;
import com.krishna.freelance.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ClientService clientService;

    public ServiceService(ServiceRepository serviceRepository, ClientService clientService) {
        this.serviceRepository = serviceRepository;
        this.clientService = clientService;
    }

    public ServiceEntity create(ServiceEntity dto) {
        // Validation
        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (dto.getHours() == null || dto.getHours() <= 0) {
            throw new IllegalArgumentException("Hours must be greater than 0");
        }
        if (dto.getRate() == null || dto.getRate() <= 0) {
            throw new IllegalArgumentException("Rate must be greater than 0");
        }
        if (dto.getDate() == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (dto.getClient() == null || dto.getClient().getId() == null) {
            throw new IllegalArgumentException("Client must be selected");
        }
        
        // ensure client exists and attach
        Long clientId = dto.getClient().getId();
        Client client = clientService.findById(clientId);
        dto.setClient(client);
        return serviceRepository.save(dto);
    }

    public List<ServiceEntity> getAll() {
        return serviceRepository.findAll();
    }

    public List<ServiceEntity> getByClient(Long clientId) {
        return serviceRepository.findByClientId(clientId);
    }
}