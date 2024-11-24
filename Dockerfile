# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml ./
COPY src ./src
COPY service_account_key.json /app/service_account_key.json
COPY target/api_rest_crud_v1-1.0-SNAPSHOT.jar /app/api_rest_crud_v1-1.0-SNAPSHOT.jar

# Set the GOOGLE_APPLICATION_CREDENTIALS environment variable for BigQuery authentication
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/service_account_key.json

# Expose the port your application runs on
EXPOSE 8080

# Set the entry point to run the JAR file
CMD ["java", "-jar", "app/api_rest_crud_v1-1.0-SNAPSHOT.jar"]
