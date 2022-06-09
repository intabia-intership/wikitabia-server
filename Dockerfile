FROM tomcat:9.0.55-jdk11-openjdk
ENV DB_CONNECT_URL="jdbc:postgresql://172.18.0.3/wikitabia"
EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]