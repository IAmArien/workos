# Build stage
FROM gradle:8.14-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# Run stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]