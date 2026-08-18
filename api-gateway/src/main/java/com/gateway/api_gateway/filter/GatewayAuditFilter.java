package com.gateway.api_gateway.filter;

import com.gateway.api_gateway.model.AuditLog;
import com.gateway.api_gateway.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class GatewayAuditFilter extends AbstractGatewayFilterFactory<GatewayAuditFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(GatewayAuditFilter.class);
    private final AuditLogRepository auditLogRepository;

    public GatewayAuditFilter(AuditLogRepository auditLogRepository) {
        super(Config.class);
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            long startTime = System.currentTimeMillis();

            return chain.filter(exchange).then(
                    Mono.defer(() -> {
                        long duration = System.currentTimeMillis() - startTime;
                        String path = exchange.getRequest().getPath().value();
                        String method = exchange.getRequest().getMethod() != null ?
                                exchange.getRequest().getMethod().name() : "UNKNOWN";

                        HttpStatusCode status = exchange.getResponse().getStatusCode();
                        int statusCode = (status != null) ? status.value() : 500;

                        String clientId = exchange.getRequest().getHeaders().getFirst("X-Client-Id");

                        AuditLog auditLog = new AuditLog();
                        auditLog.setPath(path);
                        auditLog.setMethod(method);
                        auditLog.setStatusCode(statusCode);
                        auditLog.setClientId(clientId != null ? clientId : "ANONYMOUS");
                        auditLog.setExecutionTimeMs(duration);
                        auditLog.setTimestamp(Instant.now());

                        // Properly chained reactive write without explicit .subscribe()
                        return auditLogRepository.save(auditLog)
                                .doOnSuccess(saved -> log.info("Audit log saved [Path: {}] [Status: {}] [Ms: {}]", path, statusCode, duration))
                                .doOnError(err -> log.error("Failed to save audit log: {}", err.getMessage()))
                                .then();
                    })
            );
        };
    }

    public static class Config {}
}