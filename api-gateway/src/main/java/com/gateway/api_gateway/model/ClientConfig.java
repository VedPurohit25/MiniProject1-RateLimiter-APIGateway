package com.gateway.api_gateway.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "client_configs")

public class ClientConfig {
    @Id
    private String id;
    private String apiKey;
    private String clientId;
    private int capacity;
    private int refillRatePerSec;
    private double currentTokens;
    private Instant lastRefillTimestamp;

    public ClientConfig() {}

    public ClientConfig(String apiKey, String clientId, int capacity, int refillRatePerSec) {
        this.apiKey = apiKey;
        this.clientId = clientId;
        this.capacity = capacity;
        this.refillRatePerSec = refillRatePerSec;
        this.currentTokens = capacity;
        this.lastRefillTimestamp = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getRefillRatePerSec() { return refillRatePerSec; }
    public void setRefillRatePerSec(int refillRatePerSec) { this.refillRatePerSec = refillRatePerSec; }
    public double getCurrentTokens() { return currentTokens; }
    public void setCurrentTokens(double currentTokens) { this.currentTokens = currentTokens; }
    public Instant getLastRefillTimestamp() { return lastRefillTimestamp; }
    public void setLastRefillTimestamp(Instant lastRefillTimestamp) { this.lastRefillTimestamp = lastRefillTimestamp; }
}
