# StarWars API Integration

## Project Description
This project implements a RESTful service to integrate with the Star Wars API. The solution demonstrates how to consume external APIs using Spring Boot, WebClient, and caching mechanisms, while applying best practices for clean architecture and scalable services.

## Solution Architecture
The service is built using **Spring Boot 3.x** and **Java 21**.
Key components include:
- **WebClient** for consuming external APIs.
- **Spring Data JPA** for pageable data handling.
- **Caching Caffeine** to improve performance of frequently accessed endpoints.
- **Spring security with JWT** for protecting the.
- **JUnit 5 + Mockito/MocKK** for unit and integration tests.

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
   <br><br>

5. Users

   The application includes a simple authentication and authorization mechanism to demonstrate role-based access control. 
   Includes two predefined users stored in memory.
   
   Two user roles are available

   | Username | Password   | Role    |
   | -------- | ---------- | ------- |
   | `admin`  | `admin123` | `ADMIN` |
   | `user`   | `user123`  | `USER`  |

   Access rules by endpoints
   
   | Endpoint        | Description                 | Required Role  |
   | --------------- | --------------------------- | -------------- |
   | `/people/**`    | People data                 | USER or ADMIN  |
   | `/films/**`     | Films data                  | USER or ADMIN  |
   | `/vehicles/**`  | Vehicles data               | USER or ADMIN  |
   | `/starships/**` | Starships data (restricted) | **ADMIN only** |


## Considerations
The main objective of this challenge is to demonstrate the integration with an external API and how information is processed, exposed, and secured.

For this reason:
1. A persistent database was intentionally not implemented, since storing data is not part of the functional requirements.
2. The application uses a simple in-memory user store for authentication purposes.
