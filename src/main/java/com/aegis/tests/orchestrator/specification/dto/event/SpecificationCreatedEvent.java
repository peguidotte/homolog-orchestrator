package com.aegis.tests.orchestrator.specification.dto.event;

import com.aegis.tests.orchestrator.specification.Specification;
import com.aegis.tests.orchestrator.specification.enums.SpecificationInputType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

/**
 * Event published when a Specification is created.
 * <p>
 * This event contains enriched context with actual data values instead of IDs,
 * allowing external agents (like AI services) to work without needing database access.
 */
@Schema(description = "Event published when a Specification is created - contains enriched context for AI agents")
public record SpecificationCreatedEvent(

        @Schema(description = "Specification ID for reference", example = "100")
        Long specificationId,

        // === Specification Details ===
        @Schema(description = "Specification name", example = "Create User Test")
        String name,

        @Schema(description = "Specification description")
        String description,

        @Schema(description = "Input modality used", example = "MANUAL")
        SpecificationInputType inputType,

        @Schema(description = "HTTP method", example = "POST")
        String method,

        @Schema(description = "API path", example = "/api/v1/users")
        String path,

        @Schema(description = "Detailed test objective for AI context")
        String testObjective,

        @Schema(description = "Example request payload (JSON)")
        String requestExample,

        @Schema(description = "Whether authentication is required", example = "true")
        Boolean requiresAuth,

        @Schema(description = "Whether to wait for approval before generation", example = "false")
        Boolean approveBeforeGeneration,

        @Schema(description = "Test project context")
        TestProjectContext testProject,

        @Schema(description = "Target environment context")
        EnvironmentContext environment,

        @Schema(description = "Domain context (if specified)")
        DomainContext domain,

        @Schema(description = "Authentication profile context (no credentials)")
        AuthProfileContext authProfile,

        @Schema(description = "Primary API call context (when inputType is API_CALL)")
        ApiCallContext apiCall,

        @Schema(description = "Supporting API calls for additional context")
        List<ApiCallContext> supportingApiCalls,

        // === Metadata ===
        @Schema(description = "When the specification was created")
        Instant createdAt
) {
    /**
     * Creates an enriched event from a Specification entity.
     * All related entities are loaded and converted to context objects.
     */
    public static SpecificationCreatedEvent fromEntity(Specification entity) {
        List<ApiCallContext> supportingContexts = entity.getSupportingApiCalls() != null
                ? entity.getSupportingApiCalls().stream()
                        .map(ApiCallContext::fromEntity)
                        .toList()
                : List.of();

        return new SpecificationCreatedEvent(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getInputType(),
                entity.getMethod() != null ? entity.getMethod().name() : null,
                entity.getPath(),
                entity.getTestObjective(),
                entity.getRequestExample(),
                entity.getRequiresAuth(),
                entity.getApproveBeforeGeneration(),
                TestProjectContext.fromEntity(entity.getTestProject()),
                EnvironmentContext.fromEntity(entity.getEnvironment()),
                DomainContext.fromEntity(entity.getDomain()),
                AuthProfileContext.fromEntity(entity.getAuthProfile()),
                ApiCallContext.fromEntity(entity.getApiCall()),
                supportingContexts,
                entity.getCreatedAt()
        );
    }
}

