# Use a base image with Gradle and Java pre-installed
FROM adoptopenjdk:11-jdk-hotspot AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle ./gradle

# Download and cache the Gradle distribution
RUN ./gradlew --version

# Copy the application source code
COPY . .

# Build the application
RUN ./gradlew build --no-daemon

# Use a base image with Java pre-installed
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY build/libs/promokh-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your application listens
EXPOSE 8080

# Set the command to run your application
CMD ["java", "-jar", "app.jar"]
