FROM openjdk:8-jdk-alpine
EXPOSE 8085
ARG JAR_FILE=target/authentication.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
