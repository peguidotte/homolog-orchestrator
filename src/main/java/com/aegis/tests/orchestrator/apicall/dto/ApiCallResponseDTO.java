package com.aegis.tests.orchestrator.apicall.dto;

import com.aegis.tests.orchestrator.apicall.ApiCall;
import com.aegis.tests.orchestrator.shared.model.enums.HttpMethod;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response payload representing an ApiCall")
public record ApiCallResponseDTO(

        @Schema(description = "ApiCall ID", example = "1")
        Long id,

        @Schema(description = "TestProject ID", example = "1")
        Long testProjectId,

        @Schema(description = "BaseUrl ID", example = "1")
        Long baseUrlId,

        @Schema(description = "Domain ID", example = "1")
        Long domainId,

        @Schema(description = "Route definition", example = "/invoices")
        String routeDefinition,

        @Schema(description = "HTTP method", example = "POST")
        HttpMethod method,

        @Schema(description = "Description", example = "Creates a new invoice")
        String description,

        @Schema(description = "Whether requires authentication", example = "true")
        Boolean requiresAuth,

        @Schema(description = "Creation timestamp")
        Instant createdAt,

        @Schema(description = "User who created")
        String createdBy
) {
    public static ApiCallResponseDTO fromEntity(ApiCall entity) {
        return new ApiCallResponseDTO(
                entity.getId(),
                entity.getProject().getId(),
                entity.getBaseUrl().getId(),
                entity.getDomain() != null ? entity.getDomain().getId() : null,
                entity.getRouteDefinition(),
                entity.getMethod(),
                entity.getDescription(),
                entity.getRequiresAuth(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
}
