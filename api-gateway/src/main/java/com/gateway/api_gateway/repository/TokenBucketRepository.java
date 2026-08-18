package com.gateway.api_gateway.repository;

import com.gateway.api_gateway.model.TokenBucket;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBucketRepository extends ReactiveMongoRepository<TokenBucket, String> {
}