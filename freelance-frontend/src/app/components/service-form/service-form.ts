import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ServiceService } from '../../services/service.service';
import { ClientService } from '../../services/client.service';
import { Service } from '../../models/service';
import { Client } from '../../models/client';

@Component({
  selector: 'app-service-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './service-form.html',
  styleUrl: './service-form.css'
})
export class ServiceFormComponent implements OnInit {
  service: Service = {
    client: { id: 0, name: '', email: '' },
    description: '',
    hours: 0,
    rate: 0,
    date: new Date().toISOString().split('T')[0]
  };
  
  clients: Client[] = [];
  loading = false;
  error = '';
  success = false;

  constructor(
    private serviceService: ServiceService,
    private clientService: ClientService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.clientService.getClients().subscribe({
      next: (clients) => {
        this.clients = clients;
        if (clients.length > 0) {
          this.service.client = clients[0];
        }
      },
      error: (error) => {
        this.error = 'Failed to load clients. Please try again.';
        console.error('Error loading clients:', error);
      }
    });
  }

  onSubmit(): void {
    if (!this.service.client || !this.service.description.trim() || 
        this.service.hours <= 0 || this.service.rate <= 0) {
      this.error = 'Please fill in all fields with valid values.';
      return;
    }

    this.loading = true;
    this.error = '';

    this.serviceService.createService(this.service).subscribe({
      next: (createdService) => {
        this.success = true;
        this.loading = false;
        setTimeout(() => {
          this.router.navigate(['/services']);
        }, 1500);
      },
      error: (error) => {
        this.error = 'Failed to create service. Please try again.';
        this.loading = false;
        console.error('Error creating service:', error);
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/services']);
  }

  onClientChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    const selectedClient = this.clients.find(c => c.id === Number(select.value));
    if (selectedClient) {
      this.service.client = selectedClient;
    }
  }
}
