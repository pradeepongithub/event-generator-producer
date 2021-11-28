FROM openjdk:8-alpine
ADD target/producer-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]