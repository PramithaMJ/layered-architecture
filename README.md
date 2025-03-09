# Spring Boot Layered Architecture Template

This template provides a complete structure for a Spring Boot application following a layered architecture pattern. The layers include:

1. Presentation Layer (Controllers)
2. Service Layer (Business Logic)
3. Repository Layer (Data Access)
4. Domain Layer (Entity Models)
5. Configuration Layer
6. Exception Handling
7. Security
8. Utility Classes

## Project Structure

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
│   │               │   │   └── UserRequest.java
│   │               │   └── response/
│   │               │       └── UserResponse.java
│   │               ├── config/
│   │               │   ├── ApplicationConfig.java
│   │               │   └── SecurityConfig.java
│   │               ├── exception/
│   │               │   ├── GlobalExceptionHandler.java
│   │               │   ├── ResourceNotFoundException.java
│   │               │   └── BadRequestException.java
│   │               ├── security/
│   │               │   ├── JwtTokenProvider.java
│   │               │   └── UserPrincipal.java
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
