package com.gateway.api_gateway.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "request_logs")

public class RequestLog {
    @Id
    private String id;
    private String clientId;
    private String path;
    private String method;
    private int statusCode;
    private long latencyMs;
    private Instant timestamp;

    public RequestLog() { this.timestamp = Instant.now(); }

    public RequestLog(String clientId, String path, String method, int statusCode, long latencyMs) {
        this.clientId = clientId;
        this.path = path;
        this.method = method;
        this.statusCode = statusCode;
        this.latencyMs = latencyMs;
        this.timestamp = Instant.now();
    }

    public String getId() { return id; }
    public String getClientId() { return clientId; }
    public String getPath() { return path; }
    public String getMethod() { return method; }
    public int getStatusCode() { return statusCode; }
    public long getLatencyMs() { return latencyMs; }
    public Instant getTimestamp() { return timestamp; }
}

