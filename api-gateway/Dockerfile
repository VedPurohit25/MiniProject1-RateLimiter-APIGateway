# Stage 1: Build the JAR with memory-constrained Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# Limit Maven memory usage during compilation
ENV MAVEN_OPTS="-Xmx256m"
RUN mvn clean package -DskipTests

# Stage 2: Run the application with restricted JVM heap
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Restrict JVM heap to 256MB to stay safely within Render's 512MB RAM limit
ENTRYPOINT ["java", "-Xmx256m", "-XX:+UseContainerSupport", "-jar", "app.jar"]
