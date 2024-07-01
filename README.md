# Yappi

## Getting Started

This project contains the API controllers and back-end logic as the connection to the database.

## Swagger API documentation

The API documentation is available on the following URL: http://localhost:8080/swagger-ui/index.html

## Docker

Start project local with docker-compose:

```
docker-compose up --build
```

Clean and remove docker images / container:

```
sudo docker stop $(sudo docker ps -q)  true        
sudo docker rm $(sudo docker ps -a -q)  true
sudo docker image rm $(sudo docker images -q) || true
```