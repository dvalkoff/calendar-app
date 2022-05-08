#
# Build stage
#
FROM maven:3.8.5-openjdk-18 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY .openapi-generator-ignore /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:18
COPY --from=build /home/app/target/spring-boot-docker-maven.jar /usr/local/lib/trpp.jar
EXPOSE 8080
CMD ["java", "-jar", "/usr/local/lib/trpp.jar"]
