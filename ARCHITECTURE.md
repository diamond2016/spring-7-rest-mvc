## Spring-7-Rest-Mvc — Architecture

## Project Overview
A Java Spring Boot web application used as a template and learning example. The domain models include `Beer` and `Customer`.
The original repository of the course is here: [Spring Framework 7 - Beginner to Guru](https://github.com/springframeworkguru/spring-7-rest-mvc). Forked at branch: `75-db-create-scripts`
This structure is a fork of the original project, and has a different structure of folders.

## Technology Stack (high level)

- Framework: Spring Boot (3.x — 4.x migration planned)
- Java: 21
- Build: Maven
- Databases: H2 (local/tests), MySQL (production/dev profiles)
- Migrations: Flyway
- Mapping: MapStruct
- Test: Spring Boot Starter Test, JUnit 5, AssertJ

## Project Structure
The project follows standard Maven layout and a layered package structure under the base package `guru.springframework.spring7restmvc`.

Top-level layout (simplified):

```
spring-7-rest-mvc/
├── mvnw, mvnw.cmd, pom.xml                # Maven wrapper and build descriptor
├── Dockerfile                             # (optional) Docker image configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── guru/springframework/spring7restmvc/
│   │   │       ├── Spring7RestMvcApplication.java   # Main Spring Boot application
│   │   │       ├── bootstrap/                        # Test/seed bootstrap code
│   │   │       ├── controller/                       # REST controllers (API layer)
│   │   │       ├── service/                          # Service interfaces and domain logic
│   │   │       ├── service/impl/                     # Service implementations (e.g., JPA-backed)
│   │   │       ├── repository/                       # Spring Data JPA repositories
│   │   │       ├── model/
│   │   │       │   ├── entity/                       # JPA entities (Beer, Customer, ...)
│   │   │       │   └── dto/                          # DTOs used by API layer
│   │   │       ├── mapper/                           # MapStruct mappers
│   │   │       ├── config/                           # Spring and library configuration classes
│   │   │       └── exception/                        # Global exception handlers and custom exceptions
│   │   └── resources/
│   │       ├── application.properties               # Base configuration
│   │       ├── application-localh2.properties       # H2 profile
│   │       ├── application-localmysql.properties    # MySQL profile
│   │       ├── db/migration/                        # Flyway SQL migrations (V1__, V2__...)
│   │       ├── static/                              # Static assets (CSS/JS)
│   │       └── templates/                           # Server-side templates (optional)
│   └── test/
│       └── java/
│           └── guru/springframework/spring7restmvc/   # Tests mirror main packages (unit & integration)
├── scripts/                                    # Helper scripts (e.g., DB init)
├── src/test/resources/                          # Test resources (if any)
├── target/                                      # Build output (ignored in VCS)
├── README.md
├── ARCHITECTURE.md
├── TASK.md
└── LICENSE
```

Key package responsibilities:

- `controller`: expose REST endpoints and map HTTP requests/responses
- `service`: implement business rules and transactions
- `repository`: database access (Spring Data JPA interfaces)
- `model.entity`: JPA entities
- `model.dto`: DTOs used for API input/output
- `mapper`: MapStruct interfaces to convert between entities and DTOs
- `config`: application-specific configuration (security, swagger, datasource, etc.)
- `exception`: centralized exception handling and custom exceptions

Notes and conventions:

- Keep controllers thin — delegate logic to `service` layer.
- Prefer constructor injection for Spring beans.
- Place integration tests under `src/test/java` and use profiles like `localh2` or `localmysql` as needed.
- Store Flyway migrations in `src/main/resources/db/migration` with sequential `V__` filenames.
- Use `src/main/resources` for environment-specific properties; do not commit secrets.

---
This is the structure which project will adhere to after the refactor.