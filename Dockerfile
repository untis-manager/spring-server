FROM openjdk:8
COPY ./build/libs/untis-server.jar app.jar
CMD ["java", "-jar", "app.jar"]