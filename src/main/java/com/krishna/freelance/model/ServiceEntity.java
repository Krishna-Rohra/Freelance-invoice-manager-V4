package com.krishna.freelance.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "services")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double hours;

    @Column(nullable = false)
    private Double rate;

    @Column(nullable = false)
    private LocalDate date;

    public ServiceEntity() {}

    public ServiceEntity(Long id, Client client, String description, Double hours, Double rate, LocalDate date) {
        this.id = id;
        this.client = client;
        this.description = description;
        this.hours = hours;
        this.rate = rate;
        this.date = date;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getHours() { return hours; }
    public void setHours(Double hours) { this.hours = hours; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}