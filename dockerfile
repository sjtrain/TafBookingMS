# Step 1: Use a base image with Java installed
FROM amazoncorretto:17

# Step 2: Set a working directory
WORKDIR /app

# Step 3: Copy the JAR file to the container
# Replace 'your-application.jar' with the name of your Spring Boot JAR file
COPY build/libs/BookingMS.jar app.jar

# Step 4: Expose the port your application runs on

EXPOSE 8093

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]