import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './client-list.html',
  styleUrl: './client-list.css'
})
export class ClientListComponent implements OnInit {
  clients: Client[] = [];
  loading = false;
  error = '';

  constructor(
    private clientService: ClientService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.loading = true;
    this.error = '';
    
    this.clientService.getClients().subscribe({
      next: (clients) => {
        this.clients = clients;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load clients. Please try again.';
        this.loading = false;
        console.error('Error loading clients:', error);
      }
    });
  }

  addNewClient(): void {
    this.router.navigate(['/clients/new']);
  }

  viewClientServices(clientId: number): void {
    this.router.navigate(['/services'], { queryParams: { clientId } });
  }
}
