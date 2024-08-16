# yappi backend

This repository contains the API controllers and backend logic of yappi as the connection to the database.

### 🚀 Getting Started

##### First, clone the repository to your local machine:
```bash
git clone https://github.com/24FSIIT16/dear-dev-backend.git
```

##### Create an `.env` file in the root directory of the project and add the following environment variables:

```
# Password for the yappi_db database
DATABASE_PASSWORD=your_password

# JWT secret key, used for verifying the token. 
# If you plan to use this together with the frontend, make sure to use the same secret key in the frontend.
JWT_SECRET=your_secret_key
```

##### Changes needed in the `application.properties` file. Replace spring datasource with:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/yappi_db
```

##### Build to project:
```bash
./gradlew build
```

##### Run the project:
```bash
./gradlew bootRun
```
The project will be running on `http://localhost:8080`.

##### Start the project with docker-compose:
```bash
docker compose up -d 
```
This will build the project and start it in a docker container, that includes the database as well. The project will be running on `http://localhost:8080`.

If you want to change for example the database name, you can do so in the `docker-compose.yml` file.

### 📚 Swagger API documentation

The API documentation is available on the following URL: http://localhost:8080/swagger-ui/index.html

### 🛟 Usefull Docker commands
List all running container:
```
docker ps
```

Clean and remove docker images / container

```
sudo docker stop $(sudo docker ps -q)  true
sudo docker rm $(sudo docker ps -a -q)  true
sudo docker image rm $(sudo docker images -q) || true
```

View logs of the running container:
```
docker logs -f <container_id>
```

Delete docker volumes:

```
docker volumne prune -f
```

### 📦 Built With
- Java 21
- PostgreSQL 13.0
- Spring Boot 3.3.0
- Sprint Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Web
- Spring Boot Starter Validation
- Spring Boot Starter Test
