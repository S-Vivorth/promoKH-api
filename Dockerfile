# Use a base image with Gradle and a JDK for the build stage
FROM gradle:7.3.3-jdk17 AS build

# Set the working directory inside the build container
WORKDIR /app

# Copy the entire project directory to the build container
COPY . .

# Download dependencies and build the application
RUN ./gradlew build

# Use a base image with Java and a JRE for the runtime stage
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by Spring Boot from the build stage to the runtime container
COPY --from=build /app/build/libs/promoKH-api-0.0.1-SNAPSHOT.jar app.jar
# Create a script to fetch environment variables from Railway and generate env.properties
RUN echo "#!/bin/bash" > /app/fetch_env.sh \
    && echo "echo \"Fetching environment variables from Railway...\"" >> /app/fetch_env.sh \
    && echo "railway variables get --format properties > env.properties" >> /app/fetch_env.sh \
    && chmod +x /app/fetch_env.sh

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Set the command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]
