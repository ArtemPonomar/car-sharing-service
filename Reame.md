# Car-sharing-service 

- [Description](#description)
- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies](#technologies)
- [How to Run and Test](#how-to-run-and-test)
- [Authors](#authors)

### Description
Car-Sharing-Service is a RESTful web application designed to solve car rental tasks.
The application is built on Spring Boot and utilizes Java, adhering to the principles
of the REST architectural style, ensuring stateless communication between clients and the server.
Users can easily register and log in using their credentials.
The application offers role-based authorization for administrators and regular users.
A unique feature of our service is the integration with the Stripe payment system,
which guarantees secure and reliable transactions for our users.
This allows you to pay for car rentals with maximum comfort.
We have also added a notification feature through a Telegram bot,
allowing users to quickly receive crucial information about the rental status, payments,
and any changes in the condition of the cars.
Car-Sharing-Service is a modern tool for car rental that offers speed, security,
and convenience to all its users.

### Features
- **User registration, login, and role-based authorization:** Allows different user levels to have appropriate access and capabilities within the service.
- **Exception handling with descriptive messages:** Enhances the user experience and makes debugging easier by providing clear and informative error messages.
- **Multiple endpoints with user and admin access:** Enables different functionalities and operations for both users and administrators.
- **Integration with Stripe payment service:** Offers secure and reliable transactions for car rentals, improving the user experience and trust in the service.
- **Telegram bot notifications:** Provides users with timely updates about the rental status, payments, and changes in the condition of the cars, ensuring that users are always informed.
- **Car booking and management:** Enables users to conveniently book, use, and return rental cars, with all the necessary details tracked within the service.

### Project Structure

The project follows a 3-layer architecture:

- Presentation layer (controllers)
- Application layer (services)
- Data access layer (repositories)

The project has the following package structure:

- `controllers`: Contains controllers for handling endpoints.
- `repositories`: Repository layer responsible for connecting with the database and performing CRUD operations.
- `dto`: Data transfer objects for transferring data between layers.
- `model`: Stores information about entities and their properties.
- `security`: Defines access rights to users based on their roles.
- `service`: Contains business logic services.
- `exception`: Contains exception classes for handling errors within the project.
- `config`: Contains configuration classes for setting up various aspects of the application:
    - `PasswordEncoderConfig`: Configuration for password encoding using BCrypt.
    - `TelegramBotConfig`: Configuration for interacting with the Telegram Bot API.
    - `SecurityConfig`: Security configuration including JWT authentication and role-based access control.

### Technologies

| Technology        | Version |
| ----------------- |---------|
| Java              | 17      |
| Spring Boot       | 3.1.0   |
| Springdoc OpenAPI (Swagger) | 2.0.0 |
| Liquibase         |      |
| MySQL             | 5.7     |
| Lombok            | TBD     |
| Stripe Java       | 22.21.0 |
| jjwt              | 0.9.1   |
| Telegram Bots     | 6.5.0   |
| Docker            | 23.0.5 |
| Apache Maven      | 3.8.7 |

### How to Run

1. ✅ Download and install Docker on your machine if you haven't already. Docker is required to create the project's environment. Check out Docker's [official website](https://www.docker.com/) for installation guides.
2. ✅ Clone the project repository to your local machine.
3. ✅ Inside the `src/main/resources` directory, copy the `application.properties.sample` file, rename the copy to `application.properties`, and fill in the necessary information (database connection parameters, bot tokens, JWT secret key, etc).
4. ✅ Create a `.env` file in the root directory of your project and populate it with necessary environment variables. These variables will be used by Docker Compose to setup the application.
5. ✅ From the root directory of the project, build your application by running the Maven package command: `mvn package`.
6. ✅ Once the application is built, use the following command to start the services using Docker Compose: `docker-compose up`.
7. ✅ You can use [Postman](https://www.postman.com/) to send requests to the application's endpoints for testing.

Please ensure that all necessary configurations are correctly set up before running the application.

> Remember, to stop running containers later on, you can use `docker-compose down` from the same directory where your `docker-compose.yml` file is located.

### Our team

- Artem Grunin
- Mykhailo Moskalenko
- Yurii Turchyn
- Oleksandr Koval
- Artem Ponomarenko
- Serhii Sinilov
