import { Client } from './client';

export interface Service {
  id?: number;
  client: Client;
  description: string;
  hours: number;
  rate: number;
  date: string;
}
