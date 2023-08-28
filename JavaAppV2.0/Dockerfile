# Use the official OpenJDK as the base image
FROM openjdk:8-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Java source code to the container
COPY src/StaticWebsiteServer.java /app/

# Copy the resources to the container
COPY resources /app/resources

# Compile the Java source code
RUN javac StaticWebsiteServer.java

# Expose port 8080 for the Java application
EXPOSE 8080

# Run the Java application when the container starts
CMD ["java", "src.StaticWebsiteServer"]
