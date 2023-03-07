FROM maven:3.8.3-openjdk-17-slim AS maven

WORKDIR /usr/src/app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ src/
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
ARG JAR_FILE=management-user-service-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app/

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "management-user-service-0.0.1-SNAPSHOT.jar"]

