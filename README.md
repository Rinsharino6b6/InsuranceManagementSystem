# Insurance Management System

## Overview

The **Insurance Management System** is a Java-based console application developed using JDBC and MySQL. It helps manage insurance-related operations, including customer information, insurance policies, premium payments, claims, claim documents, and policy renewals.

The application follows a layered architecture with Model, DAO, and Service components to maintain separation of concerns, code reusability, and maintainability.

## Technologies Used

- **Programming Language:** Java
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA
- **Database Driver:** MySQL Connector/J
- **Version Control:** Git and GitHub

## Features

The application consists of seven major modules:

### 1. Customer Management
- Register new customers.
- View all customer records.
- Search customers by ID.
- Update customer information.
- Delete customer records.

### 2. Insurance Type Management
- Add insurance types.
- View available insurance types.
- Search insurance types.
- Update insurance type details.
- Delete insurance types.

### 3. Policy Management
- Create insurance policies.
- Associate policies with customers and insurance types.
- View all policies.
- Search policies by ID.
- Update policy details.
- Delete policies.

### 4. Premium Payment Management
- Record premium payments.
- View all payment records.
- Search payments by ID.
- View payments associated with a policy.
- Update payment details.
- Delete payment records.
- Manage payment methods and payment statuses.

### 5. Claims Management
- Submit insurance claims.
- View all claims.
- Search claims by ID.
- View claims associated with a policy.
- Update claim status.
- Manage approved claim amounts.
- Delete claim records.

### 6. Claim Documents Management
- Add claim document records.
- Store document names and file paths.
- View all claim documents.
- Search documents by ID.
- View documents associated with a claim.
- Update and delete document records.

### 7. Policy Renewals Management
- Create policy renewal records.
- View all renewals.
- Search renewals by ID.
- View renewals associated with a policy.
- Update renewal details and status.
- Delete renewal records.

## Project Architecture

The application follows a layered architecture:

```text
InsuranceManagementSystem/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       ├── config/
│   │   │       │   └── DBConnection.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── Customer.java
│   │   │       │   ├── InsuranceType.java
│   │   │       │   ├── Policy.java
│   │   │       │   ├── PremiumPayment.java
│   │   │       │   ├── Claim.java
│   │   │       │   ├── ClaimDocument.java
│   │   │       │   └── PolicyRenewal.java
│   │   │       │
│   │   │       ├── dao/
│   │   │       │   ├── CustomerDAO.java
│   │   │       │   ├── InsuranceTypeDAO.java
│   │   │       │   ├── PolicyDAO.java
│   │   │       │   ├── PremiumPaymentDAO.java
│   │   │       │   ├── ClaimDAO.java
│   │   │       │   ├── ClaimDocumentDAO.java
│   │   │       │   └── PolicyRenewalDAO.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── CustomerService.java
│   │   │       │   ├── InsuranceTypeService.java
│   │   │       │   ├── PolicyService.java
│   │   │       │   ├── PremiumPaymentService.java
│   │   │       │   ├── ClaimService.java
│   │   │       │   ├── ClaimDocumentService.java
│   │   │       │   └── PolicyRenewalService.java
│   │   │       │
│   │   │       └── Main.java
│   │   │
│   │   └── resources/
│   │       └── db.properties
│   │
├── pom.xml
└── README.md
```

## Database Design

**Database Name:** `insurance_db`

The database consists of seven related tables:

| Table | Description |
|---|---|
| `customers` | Stores customer information. |
| `insurance_types` | Stores available insurance categories. |
| `policies` | Stores insurance policy details. |
| `premium_payments` | Stores premium payment transactions. |
| `claims` | Stores insurance claim information. |
| `claim_documents` | Stores claim document names and paths. |
| `policy_renewals` | Stores policy renewal details. |

Foreign key relationships maintain data consistency between related tables.

## Prerequisites

Before running the application, make sure you have:

- JDK 17 or a compatible Java version.
- MySQL Server 8.0 or compatible.
- IntelliJ IDEA or another Java IDE.
- Maven.
- MySQL Connector/J dependency.

## Installation and Setup

### Step 1: Clone the Repository

```bash
git clone <your-repository-url>
```

### Step 2: Open the Project

Open the cloned project in IntelliJ IDEA and allow Maven to download the required dependencies.

### Step 3: Create the Database

Open MySQL Workbench or MySQL Command Line and execute:

```sql
CREATE DATABASE insurance_db;
```

Create the seven tables using the database schema provided with the project.

### Step 4: Configure Database Connection

Update your `db.properties` file with your MySQL credentials.

```properties
db.url=jdbc:mysql://localhost:3306/insurance_db
db.username=your_mysql_username
db.password=your_mysql_password
```

Use your own MySQL username and password. Do not upload actual database credentials to GitHub.

### Step 5: Run the Application

1. Open `Main.java`.
2. Make sure Maven dependencies are loaded.
3. Run the `Main` class.
4. Select the required management module from the console menu.

## JDBC Concepts Implemented

- JDBC database connectivity.
- PreparedStatement and parameterized SQL queries.
- ResultSet processing and data mapping.
- CRUD operations.
- Auto-generated key retrieval.
- Try-with-resources for resource management.
- SQL joins and relational database operations.
- Input validation through the Service layer.
- Foreign key constraints and database relationships.

## Learning Outcomes

Through this project, I gained practical experience in:

- Developing Java applications using JDBC.
- Connecting Java applications with MySQL.
- Implementing layered application architecture.
- Designing relational database schemas.
- Performing CRUD operations.
- Handling SQL exceptions and validating user input.
- Managing relationships between multiple database tables.
- Organizing a Maven-based Java project.

## Future Enhancements

- Develop a graphical user interface or web-based frontend.
- Implement secure user authentication and role-based access.
- Support actual document uploads and storage.
- Implement transaction-based claim settlement.
- Automatically update policy details after successful renewal.
- Generate payment and claim reports.

## Author

**Rinsha K. A**

Engineering Student | Java Developer

## License

This project is developed for educational and learning purposes.