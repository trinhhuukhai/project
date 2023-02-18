FROM openjdk:17-alpine
VOLUME /tmp
LABEL maintainer="katz"
ADD target/project-0.0.1-SNAPSHOT.jar project.jar
ENTRYPOINT ["java","-jar","/project.jar"]