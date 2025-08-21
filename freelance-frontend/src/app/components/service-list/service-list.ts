import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceService } from '../../services/service.service';
import { ClientService } from '../../services/client.service';
import { Service } from '../../models/service';
import { Client } from '../../models/client';

@Component({
  selector: 'app-service-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './service-list.html',
  styleUrl: './service-list.css'
})
export class ServiceListComponent implements OnInit {
  services: Service[] = [];
  clients: Client[] = [];
  loading = false;
  error = '';
  selectedClientId: number | null = null;
  totalAmount = 0;
  totalHours = 0;

  constructor(
    private serviceService: ServiceService,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadClients();
    this.route.queryParams.subscribe(params => {
      this.selectedClientId = params['clientId'] ? Number(params['clientId']) : null;
      this.loadServices();
    });
  }

  loadClients(): void {
    this.clientService.getClients().subscribe({
      next: (clients) => {
        this.clients = clients;
      },
      error: (error) => {
        console.error('Error loading clients:', error);
      }
    });
  }

  loadServices(): void {
    this.loading = true;
    this.error = '';

    const observable = this.selectedClientId 
      ? this.serviceService.getServicesByClient(this.selectedClientId)
      : this.serviceService.getServices();

    observable.subscribe({
      next: (services) => {
        this.services = services;
        this.calculateTotals();
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load services. Please try again.';
        this.loading = false;
        console.error('Error loading services:', error);
      }
    });
  }

  onClientChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    this.selectedClientId = select.value ? Number(select.value) : null;
    this.loadServices();
  }

  addNewService(): void {
    this.router.navigate(['/services/new']);
  }

  downloadPdf(): void {
    this.serviceService.downloadPdf().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'invoice.pdf';
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error) => {
        console.error('Error downloading PDF:', error);
        alert('Failed to download PDF. Please try again.');
      }
    });
  }

  private calculateTotals(): void {
    this.totalAmount = this.services.reduce((total, service) => {
      return total + (service.hours * service.rate);
    }, 0);
    
    this.totalHours = this.services.reduce((total, service) => {
      return total + service.hours;
    }, 0);
  }

  getClientName(clientId: number): string {
    const client = this.clients.find(c => c.id === clientId);
    return client ? client.name : 'Unknown Client';
  }
}
