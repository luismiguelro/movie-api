# Stage 1: Build the application with Maven
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy project files to the working directory
COPY . /app

# Build the project with Maven
RUN mvn clean package

# Stage 2: Create a minimal OpenJDK 17 image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Expose the port that the application will use
EXPOSE 8080

# Copy the JAR file from the build stage to the current stage
COPY --from=build /app/target/movies-0.0.1-SNAPSHOT.jar /app/movies-0.0.1-SNAPSHOT.jar

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "/app/movies-0.0.1-SNAPSHOT.jar"]
