package com.gateway.api_gateway.repository;

import com.gateway.api_gateway.model.ClientConfig;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientConfigRepository extends ReactiveMongoRepository<ClientConfig, String> {
    Mono<ClientConfig> findByApiKey(String apiKey);
}
