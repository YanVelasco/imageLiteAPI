FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /app
COPY . .
RUN mvn dependency:resolve
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder ./app/target/*.jar app.jar
EXPOSE 8080
ENV POSTGRES_HOST=localhost

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=production" ,"app.jar"]