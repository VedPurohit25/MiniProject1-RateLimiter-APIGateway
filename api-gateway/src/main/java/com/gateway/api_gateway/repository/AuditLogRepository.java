package com.gateway.api_gateway.repository;

import com.gateway.api_gateway.model.AuditLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends ReactiveMongoRepository<AuditLog, String> {
}
