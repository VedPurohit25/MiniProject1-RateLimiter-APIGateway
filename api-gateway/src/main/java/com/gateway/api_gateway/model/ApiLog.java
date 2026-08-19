package com.gateway.api_gateway.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "api_logs")
public class ApiLog {

    @Id
    private String id;
    private LocalDateTime timestamp;
    private String clientIp;
    private String user;
    private String endpoint;
    private int httpStatus;
    private boolean rateLimitViolated;

    // Required by Spring Data MongoDB for deserialization
    public ApiLog() {
    }

    public ApiLog(String clientIp, String user, String endpoint, int httpStatus, boolean rateLimitViolated) {
        this.timestamp = LocalDateTime.now();
        this.clientIp = clientIp;
        this.user = user;
        this.endpoint = endpoint;
        this.httpStatus = httpStatus;
        this.rateLimitViolated = rateLimitViolated;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getClientIp() { return clientIp; }
    public void setClientIp(String clientIp) { this.clientIp = clientIp; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    public int getHttpStatus() { return httpStatus; }
    public void setHttpStatus(int httpStatus) { this.httpStatus = httpStatus; }

    public boolean isRateLimitViolated() { return rateLimitViolated; }
    public void setRateLimitViolated(boolean rateLimitViolated) { this.rateLimitViolated = rateLimitViolated; }
}