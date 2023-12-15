FROM openjdk:17-jdk-alpine
MAINTAINER saadaryf
COPY target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 80