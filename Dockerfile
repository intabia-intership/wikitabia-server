FROM openjdk:11-jdk-slim
COPY ./target/*.jar wikitabia.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "wikitabia.jar"]
