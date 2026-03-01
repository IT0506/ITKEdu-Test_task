# ITKEdu-Test_task
The following must be installed before executing this project:
Java 17
Spring Boot 3
PostgreSQL
Liquibase
Docker + Docker Compose

1. Start by mvn clean package -DskipTests
2. Start docker by docker-compose up --build -d
3. Once the application starts running successfully with build success, now create requests for creating a wallet, depositing, withdrawing and getting final balance of the transaction of the same UUID.
4. The version numbers indicate the following:
0: Create wallet
1: Deposit amount in wallet
2: Withdraw amount

The various applications and database settings can be configured by:
i) Environment variables
ii) .env file
iii) External application.yml
iv) Docker Compose overrides
