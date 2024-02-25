# ESG Rest Endpoint Application

This Spring Boot application provides RESTful endpoints for managing customer details using WebFlux and Reactive Relational Database Connectivity.

## Prerequisites

- Java Development Kit (JDK) 17
- Maven
- Docker (for running PostgreSQL)

## Setup

Before running the application, you'll need to set up a PostgreSQL database. Use the following Docker command to run a PostgreSQL container:

```bash
docker run -d \
--name postgres-container \
  -e POSTGRES_USER=guest \
  -e POSTGRES_PASSWORD=guest \
  -e POSTGRES_DB=customerdb \
  -p 5432:5432 \
  postgres:latest
```

# Application Overview

The application uses a systematic and structured approach embodying the principles of separation of concerns and code re-usability. 

## REST API Endpoints

### Add Customer

- **Endpoint:** `POST /v1/customer`
- **Request Body:** JSON representing customer details.
- **Response:** 201 Created with a success message.

### Get Customer

- **Endpoint:** `GET /v1/customer/{customerRef}`
- **Path Variable:** `customerRef` - Reference number of the customer.
- **Response:** 200 OK with customer details in JSON format.

## `R2dbcConfig` Class

### Responsibility:
- Configures R2DBC, initializing the database.
- Utilises a ConnectionFactoryInitializer bean to create the customer table if it does not exist using an SQL script.

### Approach:
- Structured as a Spring @Configuration class for database setup during application initialisation.
- Establishes a ConnectionFactoryInitializer bean, specifying the connection factory and populator.
- Leverages an SQL script (create_customer_table.sql) to define the customer table's structure.

## `CustomerHandler` Class

### Responsibility:
- Handles Http Requests related to customer operations (adding and retrieving)
- Uses `CustomerService` for processing requests and generating responses.

### Approach:
- Incorporates methods for adding a customer (addCustomer) and retrieving customer details (getCustomer).
- Leverages reactive programming with Mono to handle asynchronous processing.
- Utilises CustomerService methods to process and respond to customer-related requests.

## `HandlerTemplate` Class

### Responsibility:
- Provides a template for handling exceptions and generating appropriate HTTP responses.

### Approach:
- Implements the IHandlerTemplate interface, defining a method to handle response errors.
- Maps specific exceptions such as `DocumentNotFoundException` to corresponding HTTP response statuses.
- Enhances code re-usability for consistent error handling across different handlers.

## `ReactiveCustomerRepository` Interface

### Responsibility:
- Defines CRUD operations for the `Customer` entity.

### Approach:
- Extends ReactiveCrudRepository to inherit standard reactive CRUD methods.
- Declares a custom query method (`findByCustomerRef`) for retrieving a customer by reference.

## `CustomerService` Class

### Responsibility:
- Manages business logic for customer-related operations.
- Utilises ReactiveCustomerRepository for database interactions.

### Approach:
- Implements methods for saving customer details (`saveCustomerDetail`) and retrieving by reference (`getCustomerByRef`).
- Logs relevant information using SLF4J.
- Handles exceptions, throwing a DocumentNotFoundException when customer details are not found.

## `CustomerRouter` Class

### Responsibility:
- Defines routing configurations for customer-related endpoints.
- Utilises RouterTemplate for request logging.

### Approach:
- Utilises Spring WebFlux's functional routing API (`RouterFunctions.route`) to define routes.
- Maps routes to corresponding methods in `CustomerHandler`.
- Implements request logging through the `RouterTemplate` class.

## `RouterTemplate Class` Class

### Responsibility:
- Provides a template for logging details about incoming requests and their responses.

### Approach:
- Implements the IRouterTemplate interface with a method (`logEndPointRequest`) for logging request and response information.
- logs request path, response status, and HTTP method using SLF4J.

### Key Principles Demonstrated:

- **Separation of Concerns:**
    - Each class has a clear responsibility, focusing on a specific aspect of the application.

- **Reactive Programming:**
    - Utilisation of reactive programming for handling asynchronous operations.

- **Exception Handling:**
    - Consistent exception handling approach across handlers, enhancing code reliability..

- **Custom Query Method:**
    - Declaration of a custom query method in the `ReactiveCustomerRepository` interface.

- **Logging:**
    - Implementation of SLF4J for logging relevant information during application runtime.

In summary, the application follows best practices in Spring WebFlux development, 
incorporating dependency injection, configuration, 
separation of concerns, and reactive programming principles. 
The use of records, annotations, and exception handling contributes 
to a well-organized and maintainable codebase.


