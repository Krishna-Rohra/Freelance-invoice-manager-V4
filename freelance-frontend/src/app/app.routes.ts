import { Routes } from '@angular/router';
import { ClientListComponent } from './components/client-list/client-list';
import { ClientFormComponent } from './components/client-form/client-form';
import { ServiceListComponent } from './components/service-list/service-list';
import { ServiceFormComponent } from './components/service-form/service-form';

export const routes: Routes = [
  { path: '', redirectTo: '/clients', pathMatch: 'full' },
  { path: 'clients', component: ClientListComponent },
  { path: 'clients/new', component: ClientFormComponent },
  { path: 'services', component: ServiceListComponent },
  { path: 'services/new', component: ServiceFormComponent }
];
