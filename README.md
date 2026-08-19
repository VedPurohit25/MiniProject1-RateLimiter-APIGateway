# Multi-Module Microservices Stack with API Gateway & Rate Limiting

A production-grade, containerized Spring Boot microservice architecture featuring dynamic service discovery, centralized configuration, JWT authentication, Redis rate limiting, and circuit breaker fault tolerance.

---

## 🏗️ System Architecture

* **API Gateway (`8080`)**: Central entry point using Spring Cloud Gateway, Redis Token Bucket rate limiting, JWT validation, and Resilience4j circuit breakers.
* **Eureka Server (`8761`)**: Service discovery registry for dynamic instance lookup and load balancing.
* **Config Server (`8888`)**: Centralized configuration management for all microservices.
* **User Service (`8081`)**: User management domain service.
* **Product Service (`8082`)**: Product catalog domain service.
* **Infrastructure**: Redis (rate limit storage) & Zipkin (distributed tracing).

---

## 🛠️ Tech Stack

* **Language & Framework**: Java 17+, Spring Boot 3.x, Spring Cloud Gateway, Spring Data JPA
* **Infrastructure & Ops**: Docker, Docker Compose, Redis, Zipkin
* **Security & Resilience**: JWT Authentication, Resilience4j Circuit Breaker

---

## 🚀 Getting Started
## 🌐 Live Deployment
* **Live API Gateway URL**: `https://miniproject1-ratelimiter-apigateway.onrender.com`

### Prerequisites
* Docker & Docker Compose installed
* Java 17 SDK

### Run with Docker Compose
```bash
docker-compose up --build -d
