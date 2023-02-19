FROM openjdk:17-alpine
VOLUME /tmp
ADD target/*.jar project.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/project.jar"]
