#!/bin/bash

echo "Building the Spring Boot application and generate jar..."
mvn clean package

echo "Building and starting Docker containers..."
docker-compose up --build
