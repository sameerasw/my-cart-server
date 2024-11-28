FROM openjdk:21
WORKDIR /app
COPY ./target/ticketin.server-0.0.1-SNAPSHOT.jar /app
EXPOSE 8081
CMD ["java", "-jar", "ticketin.server-0.0.1-SNAPSHOT.jar"]