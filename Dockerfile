# Используем образ OpenJDK 21
FROM openjdk:21-jdk-slim

# Установим рабочую директорию в контейнере
WORKDIR /app

# Копируем файл Maven Wrapper и настройки проекта
COPY ./mvnw ./mvnw
COPY ./mvnw.cmd ./mvnw.cmd
COPY ./.mvn ./.mvn
COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Делаем Maven Wrapper исполняемым
RUN chmod +x mvnw

# Собираем приложение
RUN ./mvnw clean package -DskipTests

# Устанавливаем команду для запуска приложения
CMD ["./mvnw", "spring-boot:run"]

# Открываем порт приложения
EXPOSE 8080
