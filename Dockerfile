# Stage 1: Build the JAR file
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code and build the package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Export the JAR for dependent services
FROM alpine:latest AS export
WORKDIR /shared-libs

# Copy the generated JAR from the build stage
COPY --from=build /app/target/common-package-*.jar common-package-1.0.0.jar
