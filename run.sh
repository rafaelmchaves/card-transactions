#!/bin/bash

echo "Building the Spring Boot application and generate jar..."
mvn clean package

# Build and start the containers using Docker Compose
echo "Building and starting Docker containers..."
docker-compose up --build
