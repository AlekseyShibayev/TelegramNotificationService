FROM openjdk:11-jre-slim-buster

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080

# debug
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n
EXPOSE 5005

ENTRYPOINT ["java", "-jar", "/application.jar"]