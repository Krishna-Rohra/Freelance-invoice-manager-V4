import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './client-form.html',
  styleUrl: './client-form.css'
})
export class ClientFormComponent {
  client: Client = {
    name: '',
    email: ''
  };
  
  loading = false;
  error = '';
  success = false;

  constructor(
    private clientService: ClientService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (!this.client.name.trim() || !this.client.email.trim()) {
      this.error = 'Please fill in all fields.';
      return;
    }

    if (!this.isValidEmail(this.client.email)) {
      this.error = 'Please enter a valid email address.';
      return;
    }

    this.loading = true;
    this.error = '';

    this.clientService.createClient(this.client).subscribe({
      next: (createdClient) => {
        this.success = true;
        this.loading = false;
        setTimeout(() => {
          this.router.navigate(['/clients']);
        }, 1500);
      },
      error: (error) => {
        this.error = 'Failed to create client. Please try again.';
        this.loading = false;
        console.error('Error creating client:', error);
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/clients']);
  }

  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }
}
