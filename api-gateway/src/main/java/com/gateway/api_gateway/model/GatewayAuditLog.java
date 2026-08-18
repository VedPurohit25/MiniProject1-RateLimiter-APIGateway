package com.gateway.api_gateway.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit_logs")
public class GatewayAuditLog {

    @Id
    private String id;
    private String path;
    private String httpMethod;
    private String apiKey;
    private int statusCode;
    private long responseTimeMs;
    private LocalDateTime timestamp;
    private String clientIp;

    public GatewayAuditLog() {}

    public GatewayAuditLog(String path, String httpMethod, String apiKey, int statusCode, long responseTimeMs, LocalDateTime timestamp, String clientIp) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.apiKey = apiKey;
        this.statusCode = statusCode;
        this.responseTimeMs = responseTimeMs;
        this.timestamp = timestamp;
        this.clientIp = clientIp;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getPath() { return path; }
    public String getHttpMethod() { return httpMethod; }
    public String getApiKey() { return apiKey; }
    public int getStatusCode() { return statusCode; }
    public long getResponseTimeMs() { return responseTimeMs; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getClientIp() { return clientIp; }
}
