#Base image
FROM openjdk:8-jre-slim
RUN apt-get update
ADD target/interview-project-0.0.1-SNAPSHOT.war opt/app/app.war
#Directive to be executed
WORKDIR opt/app
#Used port
EXPOSE 8080
#Commands that will be executed in "exec" format when container runs
ENTRYPOINT ["java", "-jar", "app.war"]
