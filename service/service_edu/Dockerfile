# FROM openjdk:8-jdk-alpine
# VOLUME /tmp
# COPY ./target/service_edu-0.0.1-SNAPSHOT.jar service_edu-0.0.1-SNAPSHOT.jar
# ENTRYPOINT ["java","-jar","service_edu-0.0.1-SNAPSHOT.jar", "&"]

FROM java:8
EXPOSE 8080
ARG JAR_FILE
ADD target/${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]