# Freelance Management System - Frontend

This is the Angular frontend for the Freelance Management System, designed to work with the Spring Boot backend.

## Features

- **Client Management**: Add and view clients
- **Service Management**: Create and track services provided to clients
- **Invoice Generation**: Download PDF invoices for services
- **Responsive Design**: Modern, mobile-friendly interface
- **Real-time Calculations**: Automatic calculation of service totals

## Prerequisites

- Node.js (v18 or higher)
- Angular CLI (v17 or higher)
- Spring Boot backend running on `http://localhost:8080`

## Installation

1. Navigate to the project directory:
   ```bash
   cd freelance-frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   ng serve
   ```

4. Open your browser and navigate to `http://localhost:4200`

## Project Structure

```
src/
├── app/
│   ├── components/
│   │   ├── client-list/          # Client listing component
│   │   ├── client-form/          # Client creation form
│   │   ├── service-list/         # Service listing component
│   │   └── service-form/         # Service creation form
│   ├── models/
│   │   ├── client.ts            # Client interface
│   │   └── service.ts           # Service interface
│   ├── services/
│   │   ├── client.service.ts    # Client API service
│   │   └── service.service.ts   # Service API service
│   ├── app.component.ts         # Main app component
│   ├── app.routes.ts           # Routing configuration
│   └── app.config.ts           # App configuration
```

## API Endpoints

The frontend communicates with the following backend endpoints:

- `GET /api/clients` - Get all clients
- `POST /api/clients` - Create a new client
- `GET /api/services` - Get all services
- `POST /api/services` - Create a new service
- `GET /api/services/client/{clientId}` - Get services by client
- `GET /api/services/pdf` - Download PDF invoice

## Usage

### Managing Clients

1. Navigate to the Clients page
2. Click "Add New Client" to create a new client
3. Fill in the client's name and email
4. Click "Create Client" to save

### Managing Services

1. Navigate to the Services page
2. Click "Add New Service" to create a new service entry
3. Select a client from the dropdown
4. Fill in service details (description, hours, rate, date)
5. Click "Create Service" to save

### Viewing and Filtering

- Use the client filter on the Services page to view services for specific clients
- View service summaries with total hours and amounts
- Download PDF invoices using the "Download PDF" button

## Development

### Building for Production

```bash
ng build
```

### Running Tests

```bash
ng test
```

### Code Formatting

```bash
ng lint
```

## Technologies Used

- **Angular 17**: Frontend framework
- **TypeScript**: Programming language
- **CSS3**: Styling with modern features
- **RxJS**: Reactive programming
- **Angular Material**: UI components (installed but not used in this version)

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.
