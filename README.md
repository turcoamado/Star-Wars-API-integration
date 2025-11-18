# StarWars API Integration

## Project Description
This project implements a RESTful service to integrate with the Star Wars API. The solution demonstrates how to consume external APIs using Spring Boot, WebClient, and caching mechanisms, while applying best practices for clean architecture and scalable services.

## Solution Architecture
The service is built using Spring Boot 3.x and Java 21.
Key components include:
- WebClient for consuming external APIs.
- Spring Data JPA for pageable data handling.
- Caching with Caffeine to improve performance of frequently accessed endpoints.
- JWT-based security with Spring Security.
- Unit and integration tests using JUnit 5 and Mockito.

## Dependencies and Requirements
To run the project, ensure you have:

- Java 21+
- Maven 3.8+
- Spring Boot 3.x
    - spring-boot-starter-web (for building API REST)
    - spring-boot-starter-webflux (for HTTP request to User API)
- H2 in memory database
- Kotlin Standard Library and Reflection
- JUnit 5 (for unit testing)
- MockK (for mocking dependencies in unit testing)

## Installation and Execution
1. Clone the repository
    ```
    git clone https://github.com/turcoamado/starwars-integration.git
    cd starwars-integration
    git checkout main
    ```

2. Build and run tests
    ```
    mvn clean install
   ```

3. Run the application
    ```
    mvn spring-boot:run
    ```

4. Access API documentation (Swagger UI):

   Open your browser and go to:
   ```
   http://localhost:8080/swagger-ui/index.html#/
   ```
   You can interact with the API endpoints directly from the Swagger UI. Swagger provides all available endpoints.
