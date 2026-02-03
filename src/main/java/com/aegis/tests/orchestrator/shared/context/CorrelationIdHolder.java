package com.aegis.tests.orchestrator.shared.context;

import java.util.UUID;

/**
 * Manages Trace ID for distributed tracing across microservices.
 * Uses ThreadLocal to maintain context across async operations.
 * <p>
 * Trace ID follows a request through the entire call chain and all derived operations.
 */
public class CorrelationIdHolder {

    private static final ThreadLocal<String> traceId = new ThreadLocal<>();

    /**
     * Get or create a Trace ID.
     * Follows the request through the entire call chain.
     */
    public static String getOrCreateTraceId() {
        String id = traceId.get();
        if (id == null) {
            id = UUID.randomUUID().toString();
            traceId.set(id);
        }
        return id;
    }

    /**
     * Set Trace ID explicitly (useful when receiving from HTTP headers).
     */
    public static void setTraceId(String id) {
        traceId.set(id);
    }

    /**
     * Get current Trace ID.
     */
    public static String getTraceId() {
        return traceId.get();
    }

    /**
     * Clear context (call in finally block or filter cleanup).
     */
    public static void clear() {
        traceId.remove();
    }
}
