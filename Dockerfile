FROM openjdk:17-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} project.jar
ENTRYPOINT ["java","-jar","/project.jar"]