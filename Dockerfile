#Base image
FROM maven:3.6.0-jdk-8-slim AS build

COPY src /home/app/src

COPY pom.xml /home/app/pom.xml

RUN mvn  -f /home/app/pom.xml clean package

FROM openjdk:8-jre-slim

COPY --from=build /home/app/target/interview-0.0.1-SNAPSHOT.jar app.jar
#ADD /opt/app/target/interview-project-0.0.1-SNAPSHOT.war opt/app/app.war
#Used port
EXPOSE 8080
#Commands that will be executed in "exec" format when container runs
ENTRYPOINT ["java", "-jar", "app.jar"]
