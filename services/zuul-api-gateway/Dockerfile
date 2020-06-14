FROM openjdk:8-jdk-alpine
EXPOSE 8762
ARG JAR_FILE=target/zuul.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
