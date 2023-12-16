FROM openjdk:17-jdk-alpine

LABEL maintainer="saadaryf"

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
