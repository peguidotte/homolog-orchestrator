package com.aegis.tests.orchestrator.specification.dto.event;

import com.aegis.tests.orchestrator.apicall.ApiCall;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

/**
 * ApiCall data for event consumption by external agents.
 * Contains actual values for the AI to understand the endpoint.
 */
@Schema(description = "API Call context for AI agent")
public record ApiCallContext(

        @Schema(description = "API Call ID", example = "30")
        Long id,

        @Schema(description = "HTTP method", example = "POST")
        String method,

        @Schema(description = "Route definition", example = "/api/v1/users/{userId}")
        String routeDefinition,

        @Schema(description = "Full base URL", example = "https://api.staging.example.com")
        String baseUrl,

        @Schema(description = "Endpoint description", example = "Creates a new user in the system")
        String description,

        @Schema(description = "Domain this endpoint belongs to")
        DomainContext domain,

        @Schema(description = "Example request payload (JSON)")
        String requestExample,

        @Schema(description = "Example response payload (JSON)")
        String responseExample,

        @Schema(description = "Whether this endpoint requires authentication", example = "true")
        Boolean requiresAuth,

        @Schema(description = "Required parameters description (JSON)")
        String requiredParams,

        @Schema(description = "Custom variables for this endpoint")
        Set<String> customVariables
) {
    public static ApiCallContext fromEntity(ApiCall entity) {
        if (entity == null) return null;
        return new ApiCallContext(
                entity.getId(),
                entity.getMethod() != null ? entity.getMethod().name() : null,
                entity.getRouteDefinition(),
                entity.getBaseUrl() != null ? entity.getBaseUrl().getUrl() : null,
                entity.getDescription(),
                DomainContext.fromEntity(entity.getDomain()),
                entity.getRequestExample(),
                entity.getResponseExample(),
                entity.getRequiresAuth(),
                entity.getRequiredParams(),
                entity.getCustomVariables() != null ? Set.copyOf(entity.getCustomVariables()) : Set.of()
        );
    }
}
