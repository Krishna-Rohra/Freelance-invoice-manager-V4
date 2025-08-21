# Freelance Invoice Manager

A comprehensive invoice management system for freelancers built with Spring Boot, PostgreSQL, and a responsive frontend.

## 🎯 Features

- **Client Management**: Add and view clients with name and email
- **Service Management**: Record services with description, hours, rate, and date
- **PDF Invoice Generation**: Download professional PDF invoices with all service records
- **Responsive UI**: Modern, mobile-friendly interface
- **Real-time Calculations**: Automatic total calculations for services

## 🛠 Technology Stack

- **Backend**: Java 21, Spring Boot 3.5.4, Hibernate, Spring MVC
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Database**: PostgreSQL
- **PDF Generation**: OpenPDF
- **Build Tool**: Maven

## 🚀 Quick Start

### Prerequisites

- Java 21 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE freelance_db;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/freelance_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
cd freelance
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Open your browser and navigate to:
```
http://localhost:8080
```

## 📖 Usage

### Adding Clients
1. Fill in the client name and email in the "Clients" section
2. Click "Add Client"
3. The client will appear in the list below

### Adding Services
1. Select a client from the dropdown
2. Enter service description, hours worked, hourly rate, and date
3. Click "Add Service"
4. The service will appear in the services list with calculated totals

### Generating PDF Invoice
1. Click the "📄 Download Invoice PDF" button
2. A PDF file will be downloaded containing all services with totals

## 🏗 Architecture

The application follows the MVC (Model-View-Controller) pattern:

- **Model**: `Client` and `ServiceEntity` classes mapped to PostgreSQL tables
- **View**: Static HTML/CSS/JS served from Spring Boot
- **Controller**: REST endpoints for CRUD operations
- **Service Layer**: Business logic and validation
- **Repository Layer**: Data access using Spring Data JPA

## 📊 Database Schema

### Clients Table
| Column | Type | Description |
|--------|------|-------------|
| id | SERIAL PK | Unique client ID |
| name | VARCHAR | Client name |
| email | VARCHAR | Client email (unique) |

### Services Table
| Column | Type | Description |
|--------|------|-------------|
| id | SERIAL PK | Unique service ID |
| client_id | INT FK | Linked client |
| description | TEXT | Service description |
| hours | NUMERIC | Hours worked |
| rate | NUMERIC | Hourly rate |
| date | DATE | Service date |

## 🔧 API Endpoints

- `GET /api/clients` - Get all clients
- `POST /api/clients` - Create a new client
- `GET /api/services` - Get all services
- `POST /api/services` - Create a new service
- `GET /api/services/client/{clientId}` - Get services by client
- `GET /api/services/pdf` - Download PDF invoice

## 🎨 UI Features

- **Dark Theme**: Modern dark interface with accent colors
- **Responsive Design**: Works on desktop and mobile devices
- **Interactive Elements**: Hover effects and smooth transitions
- **Real-time Updates**: Automatic refresh of data after operations
- **Error Handling**: User-friendly error messages

## 📝 Example Output

### Services List
```
Website Design — 5h @ ₹200 on 2025-01-15
Client: John Doe | Total: ₹1000.00

Logo Creation — 3h @ ₹150 on 2025-01-16
Client: Jane Smith | Total: ₹450.00

Total Amount: ₹1450.00
```

### PDF Invoice
A professionally formatted PDF containing:
- Invoice header with date
- Service details table
- Individual service totals
- Grand total calculation

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify PostgreSQL is running
   - Check database credentials in `application.properties`
   - Ensure database `freelance_db` exists

2. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`

3. **PDF Download Issues**
   - Check browser download settings
   - Ensure no popup blockers are active

## 📄 License

This project is open source and available under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

---

Built with ❤️ using Spring Boot and modern web technologies.
