# Garage Management System

## Overview

Garage Management System is a Spring Boot application that allows you to park vehicles, manage garage slots, and retrieve garage status. This system supports different types of vehicles (cars, jeeps, trucks) and allocates parking slots accordingly.

## Features

- Park a vehicle in the garage.
- Leave a parking slot.
- Check the current status of the garage.
- Supports different vehicle types: car, jeep, and truck.
- Exception handling and input validation.

## Technologies Used

- Java 17
- Spring Boot
- JUnit 5
- Mockito
- Lombok

## Prerequisites

- Java 17 or higher
- Maven 4.0.0
- Postman for API testing (optional)

## Getting Started

1. Clone the repository:
    ```bash
    git clone https://github.com/ummugulsumm/garage-management-service.git
    ```
2. Build the project:
    ```bash
    mvn clean install
    ```
3. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Usage

### Endpoints

- **Park Vehicle**
    - `POST /garage/park`
    - Request Body:
      ```json
      {
        "plateNumber": "34-ABC-1234",
        "color": "Blue",
        "type": "car"
      }
      ```

- **Leave Vehicle**
    - `PATCH /garage/leave/{vehicleNumber}`
    - *vehicleNumber : The order in which the vehicle arrives at the garage


- **Get Status**
    - `GET /garage/status`

### Unit Tests

Run the unit tests with:
```bash
mvn test
```

## Postman

[Postman Collection](postman/Garage-Management.postman_collection.json)