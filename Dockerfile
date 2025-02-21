# Stage 1: Build the Angular frontend
FROM node:16 AS frontend-build
WORKDIR /app/frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# Stage 2: Build the Java backend
FROM maven:3.9-amazoncorretto-21 AS backend-build
WORKDIR /app/backend
COPY backend/pom.xml ./
RUN mvn dependency:go-offline
COPY backend/src ./src
RUN mvn package -DskipTests

# Stage 3: Final image
FROM amazoncorretto:21
WORKDIR /app

# Install Node.js and npm
RUN curl -fsSL https://rpm.nodesource.com/setup_16.x | bash - \
    && yum install -y nodejs

# Copy the built frontend
COPY --from=frontend-build /app/frontend /app/frontend

# Copy the built backend JAR
COPY --from=backend-build /app/backend/target/*.jar /app/backend.jar

# Expose ports for backend and frontend
EXPOSE 8080 4200

# Start both the backend and frontend
CMD ["sh", "-c", "java -jar /app/backend.jar & cd /app/frontend && npm start"]
