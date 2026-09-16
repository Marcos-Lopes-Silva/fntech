FROM maven:3.9-eclipse-temurin-25 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder --chown=spring:spring /app/target/*.jar app.jar

HEALTHCHECK --interval=30s --timeout=5s --start-period=40s \
  CMD wget -q --spider http://localhost:8761/actuator/health || exit 1

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]