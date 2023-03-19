FROM maven:3.8.3-openjdk-17-slim AS maven

WORKDIR /usr/src/app

COPY pom.xml .
#RUN mvn dependency:go-offline
RUN mvn clean install -U -DskipTests

COPY src/ src/
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
ARG JAR_FILE=notification-service-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app/ 

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]

