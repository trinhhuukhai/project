FROM openjdk:17-oraclelinux8
#working directory
WORKDIR /app
#copy from your Host(PC, laptop) to container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#Run this inside the image
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package spring-boot:repackage
CMD ["./mvnw", "spring-boot:run"]