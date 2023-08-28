# Use the official OpenJDK as the base image
FROM openjdk:8-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the resources and source code to the container
COPY . /app

# Compile the Java source code
RUN javac src/StaticWebsiteServer.java

# Expose port 8080 for the Java application
EXPOSE 8080

# Run the Java application when the container starts
CMD ["java", "-cp", "src", "StaticWebsiteServer"]
