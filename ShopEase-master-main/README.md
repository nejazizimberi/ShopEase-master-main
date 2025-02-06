# ShopEase - A Robust E-Commerce Platform

ShopEase is a feature-rich and scalable e-commerce platform built with **Spring Boot** and a **MySQL** database. It provides users with product management, order management, cart functionality, and more, all accessible through a RESTful API. This platform is designed to be secure, efficient, and modular.

## Features

- **User Authentication**: Secure login and registration with JWT-based authentication.
- **Product Management**: CRUD (Create, Read, Update, Delete) operations for managing products.
- **Cart Management**: Add, update, and remove items in the shopping cart.
- **Order Management**: Place and manage orders.
- **RESTful APIs**: Accessible APIs for all operations.

## Technologies Used

- **Backend**: Spring Boot (Java)
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Token)
- **Persistence**: Hibernate/JPA
- **Testing**: JUnit 5, Mockito for unit testing

## Project Setup

### Prerequisites

- **Java 17** or higher
- **Spring Boot** 2.x
- **MySQL** or another compatible relational database
- **Maven** (for dependency management)

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/your-username/ShopEase.git
   cd ShopEase

2. Configure MySQL Database:

Ensure MySQL is installed and running on your system. 
Create a new database and update the application.properties file under src/main/resources/ with your database credentials.

spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update 

3. Run the Application:

You can run the application using Maven:

bash
Copy
Edit
./mvnw spring-boot:run

4. Access the Application:

Once the application starts, you can access the API endpoints at:
http://localhost:8080/apirun directly from an IDE (such as IntelliJ or Eclipse).

  ## Testing

   Unit tests for the service and controller layers are provided using JUnit and Mockito. To run the tests:

    ./mvnw test

 ## Contributing

We welcome contributions! To contribute to this project:

1. Fork the repository.
2. Create a new branch (git checkout -b feature-name).
3. Make your changes and commit  (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature-name).
5. Open a pull request.

 ## License

This project is licensed under the MIT License - see the LICENSE file for details.
