# my-cart-server

This is an experimental ported version of the Ticketin Backend.

<img width="1005" alt="CleanShot 2024-12-11 at 11  08 30@2x" src="https://github.com/user-attachments/assets/17a59d94-6aa0-418f-a144-12446b30e75b">

## Technologies Used

- **Java**: Programming language used for development.
- **Spring Boot**: Framework for building the backend application.
- **Spring Data JPA**: For database interactions.
- **PostgreSQL**: Database used for storing data. (Using NeonPostgre)
- **WebSocket**: For real-time communication.
- **Maven**: Build and dependency management tool.

## Setup Guide

### Prerequisites

- Java 17 or higher
- Maven 3.9.9 or higher
- PostgreSQL database

### Steps

1. **Clone the repository**:
    ```sh
    git clone https://github.com/sameerasw/item-in-server.git
    cd item-in-server
    ```

2. **Set up the database**:
    - Recommended to use NeonPostgre SQL database for a similar integration.
    - Create a PostgreSQL database named `ticketin`.
    - Update the database connection details in the `.env` file.

3. **Configure environment variables**:
    - Create a `.env` file in the root directory with the following content:
        ```dotenv
        SPRING_DATASOURCE_URL=jdbc:postgresql://[database-name].aws.neon.tech/ticketin?sslmode=require
        SPRING_DATASOURCE_USERNAME=<your-database-username>
        SPRING_DATASOURCE_PASSWORD=<your-database-password>
        SERVER_PORT=1245
        SERVER_ADDRESS=0.0.0.0
        DATABSE_ACTION=update
        ```

4. **Build and run the application**:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

## Environment Setup

### Environment Variables

- **SPRING_DATASOURCE_URL**: JDBC URL for the PostgreSQL database.
- **SPRING_DATASOURCE_USERNAME**: Username for the PostgreSQL database.
- **SPRING_DATASOURCE_PASSWORD**: Password for the PostgreSQL database.
- **SERVER_PORT**: Port on which the server will run.
- **SERVER_ADDRESS**: Address on which the server will run.
- **DATABSE_ACTION**: Action to be performed on the database (e.g., `update`).

### Example `.env` File

```dotenv
SPRING_DATASOURCE_URL=jdbc:postgresql://aa-bbbbb-ccc-sdafsdafs.ap-southeast-1.aws.neon.tech/ticketin?sslmode=require
SPRING_DATASOURCE_USERNAME=ticketin_owner
SPRING_DATASOURCE_PASSWORD=samplepass
SERVER_PORT=1245
SERVER_ADDRESS=0.0.0.0
DATABSE_ACTION=update
```

## Additional Details

- **Logging**: The application logs are configured to be stored in the `./logs` directory. The logging configuration can be found in the `application.properties` file.
- **Concurrency Handling**: The application uses `ReentrantLock` to handle concurrent item purchases and releases.
- **Simulations**: The application includes simulation classes (`VendorSimulation` and `CustomerSimulation`) to simulate vendor and customer activities.

For further details, please refer to the source code and the provided documentation.

---
This project was developed by [@sameerasw](https://github.com/sameerasw) as part of my coursework at University of
Westminster, OOP module. 
