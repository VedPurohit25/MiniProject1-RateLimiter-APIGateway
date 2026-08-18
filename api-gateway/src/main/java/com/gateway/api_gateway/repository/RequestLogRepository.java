package com.gateway.api_gateway.repository;

import com.gateway.api_gateway.model.RequestLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RequestLogRepository extends ReactiveMongoRepository<RequestLog, String> {
}
