# Build backend
FROM maven:3.9.4-eclipse-temurin-21 AS backend-build

WORKDIR /app/backend

COPY backend/.mvn/ .mvn
COPY backend/mvnw .
COPY backend/pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY backend/src ./src

RUN ./mvnw package -DskipTests

FROM node:14 AS frontend-build

WORKDIR /app/frontend

COPY frontend/package*.json ./

RUN npm install

COPY frontend/ .

RUN npm run build

FROM openjdk:21-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y nodejs npm

COPY --from=backend-build /app/backend/target/*.jar app.jar

COPY --from=frontend-build /app/frontend /app/frontend

EXPOSE 8080 4200

# Start both backend and frontend
CMD java -jar app.jar & cd /app/frontend && npm start
