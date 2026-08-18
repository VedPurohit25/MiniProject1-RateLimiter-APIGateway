package com.gateway.api_gateway.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = getError(request);

        Map errorAttributes = new HashMap<>();
        HttpStatus status = determineHttpStatus(error);

        errorAttributes.put("timestamp", Instant.now().toString());
        errorAttributes.put("path", request.path());
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", error != null ? error.getMessage() : "Unexpected error occurred");
        errorAttributes.put("requestId", request.exchange().getRequest().getId());

        return errorAttributes;
    }

    private HttpStatus determineHttpStatus(Throwable error) {
        if (error instanceof ResponseStatusException rse) {
            return HttpStatus.valueOf(rse.getStatusCode().value());
        } else if (error instanceof UnauthorizedException) {
            return HttpStatus.UNAUTHORIZED;
        } else if (error instanceof RateLimitExceededException) {
            return HttpStatus.TOO_MANY_REQUESTS;
        } else if (error instanceof java.net.ConnectException) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        } else if (error instanceof java.util.concurrent.TimeoutException) {
            return HttpStatus.GATEWAY_TIMEOUT;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
