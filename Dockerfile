FROM openjdk:11-jdk-slim

COPY ./target/*.jar *.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "wikitabia.jar"]

# docker build . -t wikitabia