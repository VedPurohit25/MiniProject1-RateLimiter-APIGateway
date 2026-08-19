package com.gateway.api_gateway.repository;

import com.gateway.api_gateway.model.ApiLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiLogRepository extends ReactiveMongoRepository<ApiLog, String> {
}
