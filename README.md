# Job Application Microservice API
## Introduction

This project is a microservices-based application designed to demonstrate the integration of multiple services that communicate with each other efficiently. The architecture leverages industry-standard tools and technologies such as Spring Boot, Eureka for service discovery, RabbitMQ for messaging, Zipkin for distributed tracing, and PostgreSQL as the relational database management system.

The application is structured as a collection of loosely coupled, independently deployable services that work together to offer a complete solution for handling company data, job postings, user reviews, and secure JWT ,OAuth2 and role based authentication. Each service is containerized using Docker and orchestrated using Docker Compose for seamless setup and scaling

![App](https://github.com/user-attachments/assets/22d17b1b-1dc2-4057-a6cc-97448348b893)


### 

## Key Components

### 1. **Company Management Service (`companyms`)**

This service is responsible for handling company data, including:

-   Adding, updating, and retrieving company profiles.
-   Managing company-specific details like locations, industries, and job listings.

### 2. **Job Management Service (`jobms`)**

This service is dedicated to managing job-related information, such as:

-   Posting job listings.
-   Viewing available jobs.
-   Managing job categories and job descriptions.

### 3. **Review Management Service (`reviewms`)**

This service manages user feedback and reviews for job postings and companies. Key features include:

-   Collecting user reviews for companies and jobs.
-   Displaying aggregated review data for users to make informed decisions.

### 4. **Security Service (`security`)**

The security service is built to provide secure access to the platform using **JWT (JSON Web Tokens)** for authentication and authorization. Features include:

-   User registration and login.
-   Generating and validating JWT tokens.
-   Protecting API endpoints with role-based access control.

### 5. **API Gateway (`gateway`)**

The API Gateway routes client requests to the appropriate microservices. It also handles cross-cutting concerns such as:

-   Rate limiting
-   Load balancing
-   Security filters (authentication and authorization)
-   Centralized error handling
![gateway](https://github.com/user-attachments/assets/d74d10dc-bdd0-43be-9432-823ed5cc61bc)

### 6. **Service Registry (`servicereg`)**

**Eureka** is used as the service discovery tool, allowing services to dynamically register and find each other without hardcoding URLs. This ensures that the system remains flexible and scalable as new services are added.

### 7. **Configuration Server (`configserver`)**

The **Spring Cloud Config Server** manages external configuration for all services, ensuring that configurations are consistent across different environments (development, production, etc.).

### 8. **PostgreSQL Database (`postgres`)**

The system uses **PostgreSQL** to persist user credentials and other data, including job listings, company profiles, and reviews. This relational database is chosen for its robust support for transactions and scalability.
![basic-microservice-architecture](https://github.com/user-attachments/assets/8ef0c745-73ce-4d79-ade6-8fa762d2acdd)

### 9. **RabbitMQ**

**RabbitMQ** is used for asynchronous communication between services, providing a messaging layer for event-driven architecture. It ensures that services can communicate without tight coupling, enhancing system resilience and scalability.
![before_mssg_queue](https://github.com/user-attachments/assets/d5835779-ec2d-4b32-8a07-a9f62016ee12)

![msg_broker](https://github.com/user-attachments/assets/ae342568-35d0-4fc1-a075-290e6f0e3ee6)

![msg_broker_2](https://github.com/user-attachments/assets/fee5f056-7881-49ba-ac00-d18a3696c30d)





### 10. **Zipkin (Tracing)**

**Zipkin** is integrated for distributed tracing, allowing developers to trace requests as they travel through different microservices. This is essential for debugging and monitoring the health of the system.

### 11. **PgAdmin**

**PgAdmin** is used for managing the PostgreSQL database. It provides a user-friendly web interface for viewing and manipulating data, which helps in database administration tasks.
    
-   **Containerized**: The entire system is containerized using Docker and orchestrated using Docker Compose, making it easy to set up, deploy, and scale the application.
![dockerizing](https://github.com/user-attachments/assets/163254b5-6f70-4bb2-be94-805b04b9f71e)

-   **Deployment**: Minikube(Kubernetes Dev Environment)
    Kubernetes Architecture
![kubernetes](https://github.com/user-attachments/assets/bdd98b13-ee1e-4f0c-9cd2-854e601ee00f)


## Technologies Used

-   **Backend**: Spring Boot
-   **Service Discovery**: Eureka
-   **Configuration Management**: Spring Cloud Config
-   **Authentication**: JWT (JSON Web Tokens),OAuth2
-   **Database**: PostgreSQL
-   **Messaging**: RabbitMQ
-   **Tracing**: Zipkin
-   **API Gateway**: Spring Cloud Gateway
-   **Containerization**: Docker, Docker Compose
-   **Deployment**: Minikube(Kubernetes Dev Environment)
## Project Structure

The project consists of the following main components:

1.  **Company Management Service** (`companyms`)
2.  **Job Management Service** (`jobms`)
3.  **Review Management Service** (`reviewms`)
4.  **Security Service** (`security`)
5.  **Gateway Service** (`gateway`)
6.  **Service Registry** (`servicereg`)
7.  **Config Server** (`configserver`)
8.  **PostgreSQL Database** (`postgres`)
9.  **RabbitMQ** for messaging
10.  **Zipkin** for distributed tracing
11.  **PgAdmin** for database management


## Setup and Installation

### Prerequisites

-   **Docker**: Ensure Docker is installed and running on your machine.
-   **Docker Compose**: Ensure Docker Compose is installed.

### Steps to Run the Application

1.  Clone the repository:
`git clone <your-repository-url>
cd <your-project-directory>`

-   Navigate to the `docker-compose.yml` file directory.
    
-   Build and start all the services using Docker Compose:
`docker-compose up --build`
-   This will start all the services in the background. You can check the logs for any errors or to confirm everything started properly.
    
-   **Access the Services**:
    
    -   **API Gateway**: `http://localhost:8084`
    -   **Security Service (Login, Register)**: `http://localhost:9090`
    -   **PgAdmin** (for PostgreSQL database management): `http://localhost:5050`
    -   **Zipkin (Tracing)**: `http://localhost:9411`
    -   **Service Registry (Eureka)**: `http://localhost:8761`
-   The services will automatically register with Eureka, and you can access them through the API Gateway.
### Sample Workflow

1.  **Register a User**: Use the `/user/register` endpoint to register a new user.
2.  **Login**: Use the `/user/login` endpoint to authenticate the user and receive a JWT token.
3.  **Access Job Postings**: Use the JWT token to access job postings via the API Gateway.
4.  **Submit a Review**: Submit user feedback through the Review Management service.
# Architecture Diagram
               +---------------------+      
               |  API Gateway         |
               +---------+-----------+
                         |
           +-------------+-------------+
           |                           |
    +------+-------+             +-----+------+
    | Company MS   |             |  Job MS    |
    +--------------+             +------------+
           |                           |
           |                           |
    +------v-------+             +-----v------+
    | Review MS    |             |  Security  |
    +--------------+             +------------+
           |                           |
           |                  +--------v--------+
           |                  |  Eureka (SR)    |
           |                  +--------+--------+
           |                           |
    +------v------+             +------v-------+
    |  Config     |             |  PostgreSQL  |
    +-------------+             +--------------+
           |                           |
    +------v-------+             +-----v------+
    |  RabbitMQ    |             |   Zipkin   |
    +--------------+             +------------+

## Troubleshooting

-   **Error: Service not found in Eureka**: Ensure that all services are running properly and that Eureka server is up. Check the logs for any issues.
-   **Database connection errors**: Make sure that PostgreSQL is up and running, and the correct environment variables (`DB_DEFAULT_USERNAME`, `DB_DEFAULT_PASSWORD`) are set in the `docker-compose.yml`.


## Conclusion

This project is a comprehensive microservices application designed to showcase the integration of Spring Boot with tools like Eureka, RabbitMQ, PostgreSQL, and Zipkin. It provides a scalable architecture where each service is independently deployable and can be managed through Docker and Docker Compose. The application is designed to be secure, scalable, and easy to manage, making it suitable for a variety of real-world use cases.

## License

This project is licensed under the MIT License.


