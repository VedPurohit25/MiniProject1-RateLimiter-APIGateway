package com.gateway.api_gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class ConfigTestController {

    @Value("${custom.message:Fallback Message}")
    private String message;

    @GetMapping("/config-test")
    public Mono<String> getConfigMessage() {
        return Mono.just(message);
    }
}
