FROM openjdk:17-jdk-alpine

LABEL maintainer="saadaryf"

WORKDIR /app

COPY target/expense-tracker-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
