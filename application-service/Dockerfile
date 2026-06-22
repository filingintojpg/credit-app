FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
COPY common/pom.xml common/pom.xml
COPY application-service/pom.xml application-service/pom.xml
COPY decision-service/pom.xml decision-service/pom.xml

COPY common/src common/src
COPY application-service/src application-service/src

RUN mvn -pl common,application-service -am install -DskipTests -B

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/application-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
