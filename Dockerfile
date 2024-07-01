# Use OpenJDK 20 as parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the executable jar file
COPY build/libs/dear-dev-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
