# Этап сборки
FROM maven:3.9.6-amazoncorretto-21 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -Pdev

# Этап запуска
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]