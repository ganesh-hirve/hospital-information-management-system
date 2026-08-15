# Hospital Management System

A modular, console-based Hospital Management System built with **Java 21, Maven, JDBC, and MySQL**, designed using a layered architecture to separate presentation, business logic, data access, and persistence concerns.

The system manages core hospital operations including **user authentication, doctor management, patient registration, appointment scheduling, consultation workflows, and billing** through role-based dashboards for administrators, doctors, and receptionists.

---

## Overview

The Hospital Management System models the operational workflow of a hospital from patient registration through consultation and billing.

The application is organized around three primary user roles:

| Role              | Responsibilities                                                                                      |
| ----------------- | ----------------------------------------------------------------------------------------------------- |
| **Administrator** | Manage doctors, receptionists, and hospital staff                                                     |
| **Receptionist**  | Register patients, schedule appointments, and manage billing                                          |
| **Doctor**        | View appointments, access patient information, record consultation details, and complete appointments |

The architecture is intentionally separated into independent layers so that the presentation layer can be replaced in the future without rewriting the underlying business and database logic.

---

## Core Workflow

```text
                    ┌─────────────────────┐
                    │       Admin         │
                    │ Staff Management    │
                    └──────────┬──────────┘
                               │
                               ▼
┌───────────────┐      ┌─────────────────┐      ┌───────────────┐
│ Receptionist  │─────▶│   Appointment   │◀─────│    Doctor     │
│               │      │    Workflow     │      │               │
│ Patient       │      │                 │      │ Consultation  │
│ Registration  │      │ Scheduled       │      │ Patient Care  │
│ Appointment   │      │       ↓         │      │               │
│ Billing       │      │   Completed     │      │               │
└───────┬───────┘      └────────┬────────┘      └───────┬───────┘
        │                        │                       │
        └────────────────────────┼───────────────────────┘
                                 ▼
                       ┌──────────────────┐
                       │      MySQL       │
                       │    Persistence   │
                       └──────────────────┘
```

### Patient Lifecycle

```text
Patient Arrival
      │
      ▼
Registration
      │
      ▼
Appointment Booking
      │
      ▼
Doctor Consultation
      │
      ▼
Appointment Completed
      │
      ▼
Bill Generation
      │
      ▼
Payment
```

---

## Features

### Authentication & Authorization

* Role-based login for:

  * Administrator
  * Doctor
  * Receptionist
* User status management
* Role-based dashboard routing
* Database-backed authentication
* Parameterized SQL queries using `PreparedStatement`

### Administrator

* Register doctors
* Register receptionists
* Manage hospital staff
* View registered doctors and receptionists

### Receptionist

* Register new patients
* Store patient demographic information
* Store preliminary medical history
* Schedule appointments
* View appointment information
* Generate bills after completed consultations
* Manage payment status

### Doctor

* View scheduled appointments
* View patient information
* Review medical history
* Record consultation information
* Complete appointments

### Appointment Management

Appointments follow an explicit lifecycle:

```text
SCHEDULED
    │
    ▼
COMPLETED
```

The completion of an appointment signals that the patient can proceed to the billing workflow.

### Billing

* Retrieve doctor consultation fees
* Generate bills for completed appointments
* Track pending and paid billing status

---

## Architecture

The project follows a **Layered Architecture** with clear separation of responsibilities.

```text
┌─────────────────────────────────────────────┐
│                  View Layer                 │
│             Console User Interface          │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│               Controller Layer              │
│          Request Routing / Coordination     │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│                Service Layer                │
│              Business Logic                │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│                  DAO Layer                  │
│             Database Operations             │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│                   MySQL                    │
│                Persistence Layer            │
└─────────────────────────────────────────────┘
```

### View Layer

Located under:

```text
com.hims.view
```

Responsible for:

* Console input/output
* User interaction
* Dashboard navigation
* Role-based routing

### Controller Layer

Located under:

```text
com.hims.controller
```

Responsible for:

* Coordinating user actions
* Delegating operations to services
* Keeping UI logic separate from business logic

### Service Layer

Located under:

```text
com.hims.service
com.hims.serviceimpl
```

Responsible for:

* Business rules
* Application-level validation
* Coordinating DAO operations

Interfaces are separated from their implementations to reduce coupling.

### DAO Layer

Located under:

```text
com.hims.dao
com.hims.daoimpl
```

Responsible exclusively for database interaction.

The DAO layer:

* Executes SQL queries
* Uses JDBC
* Maps database records to DTOs
* Manages JDBC resources
* Keeps SQL concerns isolated from business logic

### DTO Layer

Located under:

```text
com.hims.dto
```

DTOs encapsulate data transferred between application layers.

This prevents database-specific objects such as `ResultSet` from leaking into the service or presentation layers.

---

## Project Structure

```text
hospital-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hims/
│   │   │           │
│   │   │           ├── controller/
│   │   │           │   ├── AdminController.java
│   │   │           │   ├── AppointmentController.java
│   │   │           │   └── PatientController.java
│   │   │           │
│   │   │           ├── dao/
│   │   │           │   ├── AppointmentDao.java
│   │   │           │   ├── DoctorDao.java
│   │   │           │   ├── PatientDao.java
│   │   │           │   └── UserDao.java
│   │   │           │
│   │   │           ├── daoimpl/
│   │   │           │   ├── AppointmentDaoImpl.java
│   │   │           │   ├── DoctorDaoImpl.java
│   │   │           │   ├── PatientDaoImpl.java
│   │   │           │   └── UserDaoImpl.java
│   │   │           │
│   │   │           ├── dto/
│   │   │           │   ├── AppointmentDTO.java
│   │   │           │   ├── DoctorDTO.java
│   │   │           │   └── PatientDTO.java
│   │   │           │
│   │   │           ├── service/
│   │   │           │   ├── AppointmentService.java
│   │   │           │   ├── DoctorService.java
│   │   │           │   ├── PatientService.java
│   │   │           │   └── UserService.java
│   │   │           │
│   │   │           ├── serviceimpl/
│   │   │           │   ├── AppointmentServiceImpl.java
│   │   │           │   ├── DoctorServiceImpl.java
│   │   │           │   ├── PatientServiceImpl.java
│   │   │           │   └── UserServiceImpl.java
│   │   │           │
│   │   │           ├── util/
│   │   │           │   └── DbConnection.java
│   │   │           │
│   │   │           ├── view/
│   │   │           │   ├── AdminDashboard.java
│   │   │           │   ├── Dashboard.java
│   │   │           │   ├── DoctorDashboard.java
│   │   │           │   └── ReceptionistDashboard.java
│   │   │           │
│   │   │           └── App.java
│   │   │
│   │   └── resources/
│   │       └── schema.sql
│   │
│   └── test/
│
├── pom.xml
├── .gitignore
└── README.md
```

---

## Technology Stack

| Technology       | Purpose                         |
| ---------------- | ------------------------------- |
| **Java 21**      | Application development         |
| **Maven**        | Dependency and build management |
| **JDBC**         | Database connectivity           |
| **MySQL**        | Relational database             |
| **JUnit 5**      | Testing                         |
| **Git / GitHub** | Version control                 |

### Database Driver

```text
mysql-connector-j
```

---

## Database Design

The system uses a relational MySQL database named:

```text
hospital_management_system
```

### Core Tables

```text
tbl_user
    │
    ├─────────────── tbl_doctor
    │
    └── authentication / role management

tbl_patient
    │
    │
    └─────────────── tbl_appointment
                            │
                            │
                     tbl_doctor
                            │
                            ▼
                        tbl_bill
```

### Main Entities

#### `tbl_user`

Stores authentication and account information.

Typical responsibilities:

* User identity
* Email
* Password
* Role
* Account status

#### `tbl_doctor`

Stores professional information associated with doctor accounts.

#### `tbl_patient`

Stores:

* Patient demographics
* Contact information
* Gender
* Medical history

#### `tbl_appointment`

Associates:

* Patient
* Doctor
* Appointment date/time
* Appointment status

#### `tbl_bill`

Stores billing information associated with completed consultations.

---

## Database Setup

### Prerequisites

Install the following:

* JDK 21
* Maven
* MySQL Server
* Git

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

---

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/ganesh-hirve/hospital-information-management-system.git
```

Navigate into the project:

```bash
cd hospital-information-management-system
```

### 2. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE hospital_management_system;
```

Then execute the SQL script:

```text
src/main/resources/schema.sql
```

This creates the required tables and initial database configuration.

### 3. Configure Database Connection

The application reads database configuration from:

```text
src/main/resources/config.properties
```

Configure it according to your local MySQL installation:

```properties
db.url=jdbc:mysql://localhost:3306/hospital_management_system
db.user=YOUR_USERNAME
db.password=YOUR_PASSWORD
```

> Never commit real database credentials, API keys, tokens, or other secrets to version control.

### 4. Build the Project

```bash
mvn clean package
```

### 5. Run the Application

Start the console application from:

```text
com.hims.view.Dashboard
```

Alternatively, run the appropriate Maven/IDE configuration for the project.

---

## Authentication Flow

The authentication process follows the layered architecture:

```text
User
 │
 ▼
Dashboard
 │
 ▼
Controller
 │
 ▼
UserService
 │
 ▼
UserServiceImpl
 │
 ▼
UserDao
 │
 ▼
UserDaoImpl
 │
 ▼
MySQL
 │
 ▼
UserDTO
 │
 ▼
Role Evaluation
 │
 ├── ADMIN
 │      ▼
 │   AdminDashboard
 │
 ├── DOCTOR
 │      ▼
 │   DoctorDashboard
 │
 └── RECEPTIONIST
        ▼
     ReceptionistDashboard
```

The DAO layer uses `PreparedStatement` for database queries, reducing the risk of SQL injection caused by directly concatenating user input into SQL statements.

---

## Engineering Principles

### 1. Separation of Concerns

Each layer has a clearly defined responsibility.

```text
View
 ↓
Controller
 ↓
Service
 ↓
DAO
 ↓
Database
```

This prevents database logic, business rules, and UI logic from becoming tightly coupled.

### 2. Interface-Driven Design

DAO and Service contracts are defined through interfaces.

Example:

```java
UserService userService = new UserServiceImpl();
```

This reduces coupling and makes future implementations easier to introduce.

### 3. DTO-Based Data Transfer

Database records are converted into DTOs before being passed to higher application layers.

This prevents `ResultSet` and database-specific implementation details from leaking into the UI.

### 4. Prepared Statements

Database operations use parameterized SQL through JDBC `PreparedStatement`.

Conceptually:

```java
PreparedStatement ps =
    connection.prepareStatement(
        "SELECT * FROM tbl_user WHERE email = ?"
    );

ps.setString(1, email);
```

This is safer than constructing SQL statements through string concatenation.

### 5. Resource Management

JDBC resources should be properly closed after use.

The preferred approach is try-with-resources:

```java
try (
    Connection connection = ...;
    PreparedStatement statement = ...;
    ResultSet resultSet = ...
) {
    // database operation
}
```

This helps prevent connection and resource leaks.

### 6. Role-Based Application Flow

Different users receive different capabilities based on their assigned role.

```text
ADMIN
  └── Staff Management

DOCTOR
  └── Patient + Appointment + Consultation

RECEPTIONIST
  └── Patient + Appointment + Billing
```

---

## Security Considerations

The project follows several security-conscious practices:

* Parameterized SQL queries
* Separation between application layers
* Database credentials kept outside source control
* Role-based application routing
* Controlled database access through DAO classes

### Important Production Hardening

This project is currently a **console-based academic/portfolio application**, not a production hospital deployment.

Before deploying a real healthcare system, additional controls would be required, including:

* Strong password hashing such as BCrypt or Argon2
* Proper authentication/session management
* Fine-grained authorization
* Encryption in transit and at rest
* Audit logging
* Secure secret management
* Input validation
* Centralized exception handling
* Database migrations
* Automated testing
* Connection pooling
* Backup and disaster recovery
* Privacy and regulatory compliance appropriate to the deployment environment

---

## Current Scope

The current implementation focuses on demonstrating:

* Core Java development
* Object-oriented design
* Layered architecture
* JDBC database interaction
* MySQL relational modeling
* DAO/Service separation
* DTO-based data transfer
* Role-based workflows
* Console application design

The system is intentionally implemented without a web framework so that the underlying Java architecture and database interaction remain explicit.

---

## Future Evolution

The architecture allows the presentation layer to evolve without fundamentally rewriting the business and persistence layers.

A potential evolution path is:

```text
Current
Java Console Application
        │
        ▼
Layered Java Application
        │
        ▼
Spring Boot REST API
        │
        ├── Spring Data JPA
        ├── Spring Security
        ├── JWT / OAuth2
        ├── Validation
        └── PostgreSQL / MySQL
        │
        ▼
React Frontend
```

Potential future improvements include:

* REST API
* Spring Boot migration
* Spring Security
* BCrypt/Argon2 password hashing
* JWT-based authentication
* React frontend
* Global exception handling
* Bean validation
* Connection pooling
* Docker containerization
* CI/CD
* Automated integration tests
* Structured application logging
* Database migration tooling

---

## Testing

The project uses **JUnit 5** for testing.

Run tests with:

```bash
mvn test
```

Build and test the complete project with:

```bash
mvn clean verify
```

---

## Git Workflow

For future changes:

```bash
git status
git add .
git commit -m "Describe the change"
git push
```

Build artifacts and environment-specific files are excluded through `.gitignore`.

---

## Limitations

The current version has several deliberate limitations:

* Console-based interface
* JDBC-based persistence
* No web API
* No frontend
* No enterprise authentication framework
* No distributed deployment
* No production secret-management system
* Limited automated test coverage
* No real-world healthcare compliance implementation

These limitations define the current project scope rather than being hidden from the user.

---

## Project Status

**Status:** Active development

**Application Type:** Console-based Java application

**Architecture:** Layered Architecture

**Database:** MySQL

**Build:** Maven

**Java Version:** 21

---

## Author

**Ganesh Hirve**

Computer Engineering Student
Java | Spring Boot | Backend Development | MySQL

---

## License

This project is intended for educational and portfolio purposes.

If you plan to reuse or distribute the project, add an appropriate open-source license such as MIT License.
