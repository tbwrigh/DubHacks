# Use an official Gradle image to build the project
FROM gradle:8-jdk21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build files (to use cached dependencies if they haven't changed)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy source code and resources into the container
COPY src/* ./src

# Build the application using Gradle
RUN gradle build --no-daemon

# Use a smaller, lighter image for the final application
FROM openjdk:21-jdk-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the JAR from the previous build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port that your Spring Boot app will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
