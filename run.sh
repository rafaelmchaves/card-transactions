#!/bin/bash

# Build the Spring Boot application JAR file
echo "Building the Spring Boot application..."
mvn clean package

# Build and start the containers using Docker Compose
echo "Building and starting Docker containers..."
docker-compose up --build
