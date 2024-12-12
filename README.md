# Employee Management Persistence Service

A REST API Service for managing employee, roles and projects data. Including creating, updating, retrieving and deleting records.
This service stores the data in an H2 database. 

---

## Table of Contents

- [Installation](#installation)
- [API Documentation](#api-documentation)


---

## Installation

### Prerequisites:
- Java 17 or later
- Maven 
- Docker

### Steps to run the project:

1. **Create a package of the application. In the root directory run the command below**
   ```bash
   mvn clean package

2. **Create the docker file**
   ```bash
   docker build --tag=em-persistence-service:latest .

3. **Follow the README in the em-service application to run this application together with em-service. If you want to run this application only run the command below.**
   ```bash
   docker run -p8887:8887 em-persistence-service:latest

## API Documentation

The OpenAPI specification can be found in: src/main/resources/em-persistence.yaml
An API Key is needed for authorisation. This can be found in the application.yaml

