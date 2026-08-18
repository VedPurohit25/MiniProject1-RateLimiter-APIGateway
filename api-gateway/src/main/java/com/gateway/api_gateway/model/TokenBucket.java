package com.gateway.api_gateway.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rate_limit_buckets")
public class TokenBucket {
    @Id
    private String key;
    private double tokens;
    private long lastRefillTimestamp;

    public TokenBucket() {}

    public TokenBucket(String key, double tokens, long lastRefillTimestamp) {
        this.key = key;
        this.tokens = tokens;
        this.lastRefillTimestamp = lastRefillTimestamp;
    }

    public String getKey() { return key; }
    public double getTokens() { return tokens; }
    public void setTokens(double tokens) { this.tokens = tokens; }
    public long getLastRefillTimestamp() { return lastRefillTimestamp; }
    public void setLastRefillTimestamp(long lastRefillTimestamp) { this.lastRefillTimestamp = lastRefillTimestamp; }
}