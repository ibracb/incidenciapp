# IncidenciApp

<p align="center">
  <img src="incidencias/src/main/webapp/assets/logo.svg" alt="IncidenciApp logo" width="128">
</p>

> An app that allows residents of a building to report issues and enables the property managers to handle them by assigning them to technicians.

![Java](https://img.shields.io/badge/Java-11-blue?style=flat&logo=openjdk&logoColor=white)
![Java EE](https://img.shields.io/badge/Java%20EE%208-%28EJB%29-007396?style=flat)
![Maven](https://img.shields.io/badge/Maven-3-red?style=flat&logo=apachemaven&logoColor=white)
![WildFly](https://img.shields.io/badge/WildFly-20.0.1-red?style=flat)
![MongoDB](https://img.shields.io/badge/MongoDB-4.x-47A248?style=flat&logo=mongodb&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat&logo=docker&logoColor=white)
![University of Murcia](https://img.shields.io/badge/University%20of%20Murcia-E03B23)

## Overview

IncidenciApp is an app that enables the property managers to manage issues reported by residents of a building. It offers a web interface for logging issues, viewing them filtered by status, assigning a technician to resolve them, and marking them as resolved.

## Demo

<p align="center">
  <video src="https://github.com/user-attachments/assets/9026bed7-0a61-49cd-91c4-742cdd2e601c" controls width="800"></video>
</p>

## Project structure

```
incidenciapp/
├── docs/                        # Additional documentation
├── incidencias/                 # Maven project
│   ├── pom.xml                  # Build Maven (WildFly, Java EE 8, Swagger, MongoDB)
│   └── src/main/
│       ├── java/                # Source code
│       ├── resources/           # Classpath resources
│       └── webapp/              # Web resources
├── postman/                     # Integration API tests with Postman
├── .env.example                 # Example environment variables
├── .gitignore                   # Files ignored by Git
├── compose.yaml                 # Docker Compose file
└── README.md                    # Main documentation
```

## Requirements

- **Java 11+**: check with `java --version`
- **Maven 3+**: check with `mvn --version`
- **Docker** and **Docker Compose**: check with `docker --version` && `docker compose version`

## Installation

Clone the repository:
```bash
git clone https://github.com/ibracb/incidenciapp.git
cd incidenciapp
```

## Configuration

Copy the [example environment file](.env.example) and edit credentials as you want:
```bash
cp .env.example .env
```
Each variable in [`.env.example`](.env.example) is commented with its description and an example value.

## Compilation and execution

Start the database (MongoDB + Mongo Express):
```bash
docker compose up -d
```

Compile and start the application with Wildfly:
```bash
cd incidencias
mvn wildfly:run
```

## Web access

- **Application:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/api/swagger-ui
- **OpenAPI specification (JSON):** http://localhost:8080/api/openapi.json
- **Mongo Express:** http://localhost:8082

## Documentation

The full documentation is available in [`docs/`](docs/).

## Academic context

- **Subject:** Software Architecture
- **Degree:** BSc in Computer Engineering
- **University:** University of Murcia
- **Year:** 2025-2026

## Authors

- **Ibrahim Cherif Barry** - [ibracb](https://github.com/ibracb)
