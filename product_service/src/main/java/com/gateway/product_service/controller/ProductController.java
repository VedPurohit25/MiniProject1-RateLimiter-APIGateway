package com.gateway.product_service.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("/{id}")
    public Map<String, Object> getProductById(@PathVariable String id) {
        return Map.of(
                "id", id,
                "productName", "Developer Mechanical Keyboard",
                "stock", 42,
                "service", "product-service (Port 8082)"
        );
    }
}