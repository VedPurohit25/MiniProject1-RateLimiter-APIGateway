# Stage 1: Ultra-low memory Maven build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# Restrict Garbage Collection memory overhead and prevent compiler process forking
ENV MAVEN_OPTS="-Xmx220m -XX:+UseSerialGC"
RUN mvn clean package -DskipTests -Dmaven.compiler.fork=false

# Stage 2: Lightweight runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx256m", "-XX:+UseSerialGC", "-XX:+UseContainerSupport", "-jar", "app.jar"]
