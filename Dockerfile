# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY src /app/src
COPY pom.xml /app/pom.xml
COPY service_account_key.json /app/service_account_key.json
COPY target/api_rest_crud_v1-1.0-SNAPSHOT.jar /app/api_rest_crud_v1-1.0-SNAPSHOT.jar

ENV GOOGLE_APPLICATION_CREDENTIALS=/app/service_account_key.json

EXPOSE 8080

CMD ["sh", "-c", "java -jar /app/api_rest_crud_v1-1.0-SNAPSHOT.jar --server.port=$PORT"]
