package com.gateway.api_gateway.filter;

import com.gateway.api_gateway.model.ApiLog;
import com.gateway.api_gateway.repository.ApiLogRepository;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MongoRateLimiterFilter extends AbstractGatewayFilterFactory<MongoRateLimiterFilter.Config> {

    private final ApiLogRepository apiLogRepository;
    private final Map<String, TokenBucketState> bucketMap = new ConcurrentHashMap<>();

    private static final double CAPACITY = 5.0;
    private static final double REFILL_RATE_PER_SEC = 0.5;

    public MongoRateLimiterFilter(ApiLogRepository apiLogRepository) {
        super(Config.class);
        this.apiLogRepository = apiLogRepository;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String clientId = request.getHeaders().getFirst("X-Client-Id");
            String clientIp = request.getRemoteAddress() != null ?
                    request.getRemoteAddress().getAddress().getHostAddress() : "UNKNOWN";

            String rateLimitKey = clientId != null ? clientId : clientIp;
            String endpoint = request.getPath().value();

            boolean allowed = tryConsume(rateLimitKey);

            if (!allowed) {
                // Safely log 429 violation to MongoDB without crashing reactive pipeline on DB error
                return apiLogRepository.save(new ApiLog(clientIp, clientId, endpoint, 429, true))
                        .onErrorResume(ex -> Mono.empty())
                        .then(handleRateLimitExceeded(exchange));
            }

            // Process request and log status code safely on completion
            return chain.filter(exchange).then(Mono.defer(() -> {
                int statusCode = exchange.getResponse().getStatusCode() != null ?
                        exchange.getResponse().getStatusCode().value() : 200;
                return apiLogRepository.save(new ApiLog(clientIp, clientId, endpoint, statusCode, false))
                        .onErrorResume(ex -> Mono.empty())
                        .then();
            }));
        };
    }

    private synchronized boolean tryConsume(String key) {
        long now = System.currentTimeMillis();
        TokenBucketState bucket = bucketMap.computeIfAbsent(key, k -> new TokenBucketState(CAPACITY, now));

        long elapsedMs = Math.max(0, now - bucket.lastRefill);
        double addedTokens = (elapsedMs / 1000.0) * REFILL_RATE_PER_SEC;
        bucket.tokens = Math.min(CAPACITY, bucket.tokens + addedTokens);
        bucket.lastRefill = now;

        if (bucket.tokens >= 1.0) {
            bucket.tokens -= 1.0;
            return true;
        }
        return false;
    }

    private Mono<Void> handleRateLimitExceeded(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();

        // Prevent writing to an already committed response sink
        if (response.isCommitted()) {
            return Mono.empty();
        }

        response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().set("Retry-After", "1");

        String path = exchange.getRequest().getPath().value();
        String jsonResponseBody = String.format("""
            {
              "timestamp": "%s",
              "status": 429,
              "error": "Too Many Requests",
              "message": "API request limit exceeded. Token bucket depleted.",
              "path": "%s"
            }
            """, Instant.now(), path);

        DataBuffer buffer = response.bufferFactory().wrap(jsonResponseBody.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer))
                .onErrorResume(ex -> Mono.empty());
    }

    private static class TokenBucketState {
        double tokens;
        long lastRefill;

        TokenBucketState(double tokens, long lastRefill) {
            this.tokens = tokens;
            this.lastRefill = lastRefill;
        }
    }

    public static class Config {}
}