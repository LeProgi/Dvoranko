FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw verify

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/dvoranko-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD sh -c "java -jar app.jar"
