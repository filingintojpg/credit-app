FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
COPY common/pom.xml common/pom.xml
COPY application-service/pom.xml application-service/pom.xml
COPY decision-service/pom.xml decision-service/pom.xml

COPY common/src common/src
COPY decision-service/src decision-service/src

RUN mvn -pl common,decision-service -am install -DskipTests -B

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/decision-service/target/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
