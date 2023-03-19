FROM maven:3.8.3-openjdk-17-slim AS maven

WORKDIR /usr/src/app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ src/
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
ARG JAR_FILE=notification-service-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app/

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app

COPY --from=maven /usr/src/app/message-broker-service/target/message-broker-service-0.0.1-SNAPSHOT.jar /usr/src/app/lib/

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]

