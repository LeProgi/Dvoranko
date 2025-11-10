FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY source_code/backend/dvoranko-backend/mvnw .
COPY source_code/backend/dvoranko-backend/.mvn .mvn
COPY source_code/backend/dvoranko-backend/pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY source_code/backend/dvoranko-backend/src ./src
RUN ./mvnw verify

FROM node:20-alpine AS build-frontend

WORKDIR /frontend

COPY source_code/frontend/dvoranko-frontend/package*.json ./
RUN npm install --frozen-lockfile

COPY frontend/ ./
RUN npm run build

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/dvoranko-0.0.1-SNAPSHOT.jar app.jar

COPY --from=build-frontend /frontend/dist ./src/main/resources/static

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
