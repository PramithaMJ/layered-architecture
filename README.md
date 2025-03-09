# Spring Boot Layered Architecture Template

## Overview

This repository provides a complete template for building Spring Boot applications following a clean layered architecture. It includes all necessary configurations, security setup, and best practices for creating scalable and maintainable Java applications.

## Architecture

The application follows a standard layered architecture pattern:

1. **Presentation Layer (Controllers)**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic and orchestration
3. **Repository Layer**: Manages data access operations
4. **Domain Layer**: Defines the core business entities and data models
5. **Configuration Layer**: Manages application configuration
6. **Security Layer**: Handles authentication and authorization
7. **Exception Handling**: Provides centralized error handling

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── demo/
│   │               ├── DemoApplication.java
│   │               ├── controller/
│   │               │   ├── UserController.java
│   │               │   └── ApiResponse.java
│   |               |   └── AuthController.java
│   │               ├── service/
│   │               │   ├── UserService.java
│   │               │   └── UserServiceImpl.java
│   │               ├── repository/
│   │               │   └── UserRepository.java
│   │               ├── entity/
│   │               │   └── User.java
│   │               ├── dto/
│   │               │   ├── UserDto.java
│   │               │   ├── request/
│   │               │   │   |── UserRequest.java
│   |               |   |   |── LoginRequest.java
│   |               |   |   └── SignupRequest.java
│   │               │   └── response/
│   │               │       |── UserResponse.java
│   │               │       └── JwtAuthenticationResponse.java
│   │               ├── config/
│   │               │   ├── ApplicationConfig.java
│   │               │   └── SecurityConfig.java
│   │               ├── exception/
│   │               │   ├── GlobalExceptionHandler.java
│   │               │   ├── ResourceNotFoundException.java
│   │               │   └── BadRequestException.java
│   │               ├── security/
│   │               │   ├── JwtTokenProvider.java
│   │               │   |── UserPrincipal.java
│   │               │   |── JwtAuthenticationFilter.java
│   │               │   └── UserDetailsServiceImpl.java
│   │               └── util/
│   │                   └── AppConstants.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       ├── application-prod.properties
│       └── logback.xml
└── test/
    └── java/
        └── com/
            └── example/
                └── demo/
                    ├── controller/
                    │   └── UserControllerTest.java
                    ├── service/
                    │   └── UserServiceTest.java
                    └── repository/
                        └── UserRepositoryTest.java
```

## Features

- **Complete Layered Architecture**: Clear separation of concerns with proper layering
- **JWT Authentication**: Secure authentication and authorization system
- **Global Exception Handling**: Centralized error handling for consistent responses
- **Database Integration**: JPA/Hibernate with support for multiple databases
- **API Documentation**: OpenAPI (Swagger) integration
- **Actuator**: Application monitoring and metrics
- **Testing Framework**: Comprehensive test setup for all layers
- **Multi-environment Configuration**: Development, testing, production profiles
- **Lombok Integration**: Reduced boilerplate code
- **DTO Mapping**: Clean data transfer between layers

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven 3.6+ or Gradle 7.0+
- PostgreSQL (for production) or H2 (for development)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/spring-boot-layered-architecture.git
   cd spring-boot-layered-architecture
   ```

2. **Configure the database**
   
   Edit `application-dev.properties` for development:
   ```properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   ```

   Edit `application-prod.properties` for production:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
   spring.datasource.username=postgres
   spring.datasource.password=yourpassword
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

3. **Configure JWT Settings**
   
   Edit `application.properties`:
   ```properties
   app.jwt.secret=your-secret-key-should-be-very-long-and-secure
   app.jwt.expiration=86400000
   ```

4. **Build the application**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

### API Endpoints

#### Authentication
- `POST /api/auth/signup`: Register a new user
- `POST /api/auth/signin`: Authenticate and get JWT token

#### User Management
- `GET /api/users`: Get all users (Admin only)
- `GET /api/users/{id}`: Get user by ID
- `GET /api/users/username/{username}`: Get user by username
- `PUT /api/users/{id}`: Update user
- `DELETE /api/users/{id}`: Delete user (Admin only)

### API Documentation

The API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Security

### Role-Based Access Control

The application implements role-based security:
- `ROLE_USER`: Basic user access
- `ROLE_ADMIN`: Administrative access with additional privileges

### JWT Authentication

- Token-based authentication using JWT
- Stateless authentication mechanism
- Access tokens with configurable expiration

## Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
```bash
mvn jacoco:report
```
The coverage report will be available at `target/site/jacoco/index.html`

## Building for Production

```bash
mvn clean package -Pprod
```

Run with production profile:
```bash
java -jar -Dspring.profiles.active=prod target/demo-0.0.1-SNAPSHOT.jar
```

## Customization

### Adding New Entity

1. Create entity class in the `entity` package
2. Create repository interface in the `repository` package
3. Create DTOs in the `dto` package
4. Create service interface and implementation
5. Create controller for REST endpoints
6. Add tests for each layer

## Best Practices

- Use DTOs for data transfer between layers
- Keep controllers thin, push business logic to services
- Use Spring Security annotations for method-level security
- Follow SOLID principles throughout the codebase
- Write unit tests for each layer

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.