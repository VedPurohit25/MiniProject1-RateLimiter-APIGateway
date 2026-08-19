package com.gateway.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String id) {
        log.info("Fetching user details for ID: {}", id);
        return ResponseEntity.ok(Map.of(
                "id", id,
                "username", "dev_user_" + id,
                "status", "ACTIVE",
                "service", "user-service (Port 8081)",
                "timestamp", System.currentTimeMillis()
        ));
    }
}