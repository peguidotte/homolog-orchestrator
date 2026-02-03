package com.aegis.tests.orchestrator.shared.filter;

import com.aegis.tests.orchestrator.shared.context.CorrelationIdHolder;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * HTTP Filter to capture or generate Trace ID from request headers.
 * Trace ID follows the request through the entire call chain across microservices.
 * <p>
 * Expected header: X-Trace-ID
 * If header is missing, a new ID is generated.
 */
@Component
public class CorrelationIdFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(CorrelationIdFilter.class);

    private static final String TRACE_ID_HEADER = "X-Trace-ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // Extract or generate Trace ID
            String traceId = httpRequest.getHeader(TRACE_ID_HEADER);
            if (traceId == null || traceId.isBlank()) {
                traceId = CorrelationIdHolder.getOrCreateTraceId();
            } else {
                CorrelationIdHolder.setTraceId(traceId);
            }

            // Add to response header
            httpResponse.setHeader(TRACE_ID_HEADER, traceId);

            log.debug("Request - Trace ID: {}", traceId);

            chain.doFilter(request, response);
        } finally {
            CorrelationIdHolder.clear();
        }
    }
}
