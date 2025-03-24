# Instructions.md - Popcorn Palace Movie Ticket Booking System

## Overview
The Popcorn Palace Movie Ticket Booking System is a backend service designed to handle various operations related to movie, showtime, and booking management.

## Prerequisite
1. Java SDK - https://www.oracle.com/java/technologies/downloads/#java21
2. Java IDE - https://www.jetbrains.com/idea/download or any other IDE
3. Docker - https://www.docker.com/products/docker-desktop/

## Instructions
1. Clone the Repository.
2. Navigate to the project directory: `cd popcorn-palace`
3. Run Docker to spin up the PostgreSQL container: `docker-compose up -d`
4. Build and run the application: `mvn clean spring-boot:run`

## Running Tests
1. Run the tests: `mvn test`

## Stop the Application
1. Press `ctrl+c` in the terminal.
2. Remove the Docker container: `docker-compose down`
