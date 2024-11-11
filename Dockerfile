# Stage 1: Build stage
FROM maven:3.9.0-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy the pom.xml and other necessary files to download dependencies
COPY pom.xml .

# Download dependencies (without copying source code yet to leverage cache)
RUN mvn dependency:go-offline

# Now copy the rest of the application source code
COPY src /app/src

# Build the application using Maven (Spring Boot will repackage the app into a JAR file)
RUN mvn clean package -DskipTests

# Stage 2: Run stage (smaller, production-ready image)
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app will run on (default for Spring Boot is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
