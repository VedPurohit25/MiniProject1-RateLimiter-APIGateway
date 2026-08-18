package com.gateway.api_gateway;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm; // 1. Added Import
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@SpringBootTest
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void generateValidTokenForTesting() {
        String secret = "401b461234567890abcdef1234567890abcdef1234567890abcdef1234567890";
        String token = Jwts.builder()
                .setSubject("dev_client")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256) // 2. Explicit Algorithm
                .compact();

        System.out.println("\n--------------------------------------------------");
        System.out.println("Generated Test Token:\n" + token);
        System.out.println("--------------------------------------------------\n");
    }
}