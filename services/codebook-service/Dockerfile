FROM openjdk:8-jdk-alpine
EXPOSE 8086
ARG JAR_FILE=target/codebook.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
