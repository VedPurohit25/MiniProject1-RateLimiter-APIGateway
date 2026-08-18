package com.gateway.api_gateway.config;

import com.gateway.api_gateway.model.ClientConfig;
import com.gateway.api_gateway.repository.ClientConfigRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {
    @Bean
    CommandLineRunner initDatabase(ClientConfigRepository repository) {
        return args -> {
            repository.findByApiKey("test_key_123")
                    .switchIfEmpty(repository.save(new ClientConfig("test_key_123", "dev_client", 10, 2)))
                    .subscribe(config -> System.out.println("[DB-Seeder] Default test API key initialized: " + config.getApiKey()));
        };
    }
}

