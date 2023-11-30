# Project Documentation

Welcome to the Flight Management System documentation. Below, you'll find key information about the project, its technologies, and core functionalities.

## Technologies Used

### Backend
- Spring Boot
- Spring
- Hibernate
- JPA
- Java 11

### Database
- SQL Database (H2 Console for development)
- Optional: NoSQL (MongoDb) - Can be used in future implementations

## Core Functionality

### User Management
- Login to the system with username and password.
- Create and store user data in the database.
- Display all users in the UI with editable options.
- Update or delete stored data with validations.

### Flight Management
- Create and store airline, aircraft, station, and flight data.
- Display all data in the UI with editable options.
- Update or delete stored data with validations.

## Additional Functionalities

### User Role Management (Admin and Regular Users)
- User roles: "Admin" and "User."
- Assign roles to users during registration or through an admin interface.

### Kafka Integration for Event Streaming
- Event publishing and consumption through Kafka clusters.

### Consumer Project for Kafka Messages and NoSQL Database
- Separate project to process Kafka messages and store relevant information in a NoSQL database.

### Keycloak Integration
- Integration with Keycloak for authentication.

### Test Writing
- Unit tests for critical functionalities.

### Redis Cache Usage
- Integration of Redis for caching and improved performance.


