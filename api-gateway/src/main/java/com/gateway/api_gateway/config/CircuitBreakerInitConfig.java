package com.gateway.api_gateway.config; // Ensure this matches your package declaration

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerInitConfig {

    @Bean
    public ApplicationRunner initCircuitBreakers(CircuitBreakerRegistry registry) {
        return args -> {
            // Eagerly registers the circuit breaker into Actuator's global registry on startup
            registry.circuitBreaker("userServiceCircuitBreaker");
        };
    }
}
