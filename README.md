
## Project Overview

Full-stack invoice management for freelancers. Manages clients, services, and generates PDF invoices. Built with Spring Boot and Angular.

## Technology Stack

### Backend
- Java 21
- Spring Boot 3.5.4
- Spring Data JPA (Hibernate)
- Spring Web MVC
- H2 Database (in-memory for development)
- OpenPDF 1.3.30 (PDF generation)
- Maven (build tool)

### Frontend
- Angular 20.1.0
- TypeScript 5.8.2
- RxJS 7.8.0
- Angular Material 20.1.6 (installed)
- Angular Router
- CSS3

### Development Tools
- Spring Boot DevTools
- Angular CLI 20.1.6
- Maven Wrapper

## Project Architecture

### Backend Architecture (Spring Boot)
```
src/main/java/com/krishna/freelance/
├── FreelanceApplication.java       # Main application class with CORS config
├── controller/
│   ├── ClientController.java       # REST endpoints for clients
│   └── ServiceController.java      # REST endpoints for services & PDF
├── model/
│   ├── Client.java                 # Client entity (JPA)
│   └── ServiceEntity.java          # Service entity (JPA with lazy loading)
├── repository/
│   ├── ClientRepository.java       # JPA repository for clients
│   └── ServiceRepository.java      # JPA repository with custom queries
└── service/
    ├── ClientService.java          # Business logic for clients
    ├── ServiceService.java         # Business logic for services
    └── PdfService.java             # PDF generation service
```

### Frontend Architecture (Angular)
```
freelance-frontend/src/app/
├── app.ts                          # Root component with router links
├── app.config.ts                   # App configuration (HTTP client, router)
├── app.routes.ts                   # Route definitions
├── components/
│   ├── client-form/                # Create/edit client form
│   ├── client-list/                # Display all clients
│   ├── service-form/               # Create/edit service form
│   └── service-list/               # Display services with filters
├── models/
│   ├── client.ts                   # Client TypeScript interface
│   └── service.ts                  # Service TypeScript interface
└── services/
    ├── client.service.ts           # HTTP service for clients API
    └── service.service.ts          # HTTP service for services API
```

## Core Features

### 1. Client Management
- Add clients (name, email)
- View list of clients
- Email validation
- Unique email constraint

### 2. Service Management
- Add services linked to clients
- Track description, hours, rate, date
- Automatic total calculation (hours × rate)
- Filter services by client
- Summary statistics (total hours, total amount)

### 3. PDF Invoice Generation
- Download PDF invoice with all services
- Invoice header with generation date
- Service details table
- Individual and grand total calculations
- Formatted with OpenPDF library

### 4. User Interface
- Responsive design
- Router-based navigation
- Real-time form validation
- Error handling with messages
- Loading states
- Success feedback

## Database Schema

### Clients Table
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier |
| `name` | VARCHAR | NOT NULL | Client name |
| `email` | VARCHAR | NOT NULL, UNIQUE | Client email address |

### Services Table
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier |
| `client_id` | BIGINT | FOREIGN KEY, NOT NULL | Reference to clients.id |
| `description` | VARCHAR | NOT NULL | Service description |
| `hours` | DOUBLE | NOT NULL | Hours worked |
| `rate` | DOUBLE | NOT NULL | Hourly rate |
| `date` | DATE | NOT NULL | Service date |

Relationship: Many Services → One Client (Many-to-One)

## API Endpoints

### Client Endpoints
- `GET /api/clients` - Get all clients
  - Response: `List<Client>`
- `POST /api/clients` - Create a new client
  - Request Body: `{ "name": "string", "email": "string" }`
  - Response: `Client` (201 Created) or error message (400 Bad Request)

### Service Endpoints
- `GET /api/services` - Get all services (with clients eagerly loaded)
  - Response: `List<ServiceEntity>`
- `POST /api/services` - Create a new service
  - Request Body: `{ "client": { "id": number }, "description": "string", "hours": number, "rate": number, "date": "YYYY-MM-DD" }`
  - Response: `ServiceEntity` (201 Created) or error message (400 Bad Request)
- `GET /api/services/client/{clientId}` - Get services by client ID
  - Response: `List<ServiceEntity>`
- `GET /api/services/pdf` - Download PDF invoice
  - Response: PDF file (application/pdf)
  - Headers: Content-Disposition: attachment; filename="invoice.pdf"

## Component Details

### Frontend Components

#### 1. Client Form Component
- Purpose: Create new clients
- Features:
  - Name and email input fields
  - Email format validation
  - Success/error messages
  - Navigation to client list after creation

#### 2. Client List Component
- Purpose: Display all clients
- Features:
  - List of all clients
  - "Add New Client" button
  - Loading and error states
  - Navigation to service list filtered by client

#### 3. Service Form Component
- Purpose: Create new services
- Features:
  - Client dropdown selection
  - Description, hours, rate, date inputs
  - Input validation (hours > 0, rate > 0)
  - Success/error messages

#### 4. Service List Component
- Purpose: Display services with filtering
- Features:
  - Display all services in a table
  - Client filter dropdown
  - Summary statistics (total hours, total amount)
  - PDF download button
  - "Add New Service" button

### Backend Services

#### ClientService
- Validation: Name and email required, email format check
- Operations: Create, get all, find by ID

#### ServiceService
- Validation: Description, hours > 0, rate > 0, date, and client required
- Operations: Create, get all (with eager client loading), get by client ID

#### PdfService
- Generates PDF invoices using OpenPDF
- Features: Professional formatting, tables, calculations, headers

## Configuration

### Backend Configuration (`application.properties`)
```properties
# Server runs on port 8080
server.port=8080

# H2 In-Memory Database (for development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

# H2 Console enabled at /h2-console
spring.h2.console.enabled=true

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# CORS configured in Java (FreelanceApplication.java)
```

### CORS Configuration
- Allowed Origins: `http://localhost:4200`
- Allowed Methods: GET, POST, PUT, DELETE, OPTIONS
- Allowed Headers: *
- Credentials: Enabled

## Key Implementation Details

### Recent Fixes Applied

1. Router directives fix
   - Added `RouterLink` and `RouterLinkActive` imports to App component

2. Lazy loading fix
   - Updated `ServiceRepository` with `JOIN FETCH` queries to eagerly load client relationships
   - Prevents `LazyInitializationException` when generating PDFs or serializing JSON

3. Configuration cleanup
   - Removed invalid CORS properties from `application.properties`
   - Added explicit server port configuration

### Database Strategy
- Uses H2 in-memory database for development
- PostgreSQL support available (commented in pom.xml)
- JPA automatically creates tables on startup (`ddl-auto=update`)
- All client relationships are eagerly loaded via `JOIN FETCH`

## How to Run the Project

### Prerequisites
- Java 21 or higher
- Node.js 18 or higher
- Maven 3.6 or higher
- npm (comes with Node.js)

### Backend Setup
```bash
# Navigate to project root
cd Freelance-invoice-manager-V4-main

# Build and run Spring Boot application
mvn spring-boot:run
```

Backend will start on: `http://localhost:8080`

### Frontend Setup
```bash
# Navigate to frontend directory
cd freelance-frontend

# Install dependencies (if not already done)
npm install

# Start Angular development server
npx ng serve
# or if Angular CLI is installed globally:
ng serve
```

Frontend will start on: `http://localhost:4200`

### Access Points
- Frontend Application: `http://localhost:4200`
- Backend API: `http://localhost:8080/api`
- H2 Database Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## Project Structure Overview

```
Freelance-invoice-manager-V4-main/
├── freelance-frontend/              # Angular frontend
│   ├── src/
│   │   ├── app/                    # Application code
│   │   ├── index.html              # Entry HTML
│   │   └── main.ts                 # Bootstrap file
│   ├── angular.json                # Angular configuration
│   ├── package.json                # Node dependencies
│   └── tsconfig.json               # TypeScript config
├── src/
│   ├── main/
│   │   ├── java/...                # Java source code
│   │   └── resources/
│   │       ├── application.properties  # Config
│   │       └── static/             # Static assets
│   └── test/                       # Unit tests
├── pom.xml                         # Maven configuration
├── mvnw                            # Maven wrapper (Unix)
└── mvnw.cmd                        # Maven wrapper (Windows)
```

## Data Flow

1. User interacts with Angular UI
2. Angular service makes HTTP request to Spring Boot API
3. Controller receives request and validates input
4. Service layer processes business logic
5. Repository layer queries database via JPA
6. Data returned through service → controller → HTTP response
7. Angular receives response and updates UI
8. For PDF: Service generates PDF and returns as binary stream

## Security Considerations

- CORS configured for development (localhost only)
- Input validation on both frontend and backend
- Email uniqueness enforced at database level
- SQL injection protection via JPA/Hibernate parameterized queries

## Development Notes

- H2 database resets on application restart (in-memory)
- JPA automatically creates/updates schema
- All SQL queries are logged (`spring.jpa.show-sql=true`)
- Spring Boot DevTools enables hot reload
