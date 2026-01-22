package com.aegis.tests.orchestrator.apicall.dto;

import com.aegis.tests.orchestrator.shared.model.enums.HttpMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating an ApiCall")
public record CreateApiCallRequestDTO(

        @Schema(description = "TestProject ID", example = "1")
        @NotNull(message = "TestProjectId is required")
        Long testProjectId,

        @Schema(description = "BaseUrl ID", example = "1")
        @NotNull(message = "BaseUrlId is required")
        Long baseUrlId,

        @Schema(description = "Domain ID (optional)", example = "1")
        Long domainId,

        @Schema(description = "Route/path definition", example = "/invoices", maxLength = 256)
        @NotBlank(message = "RouteDefinition is required")
        @Size(max = 256, message = "RouteDefinition must have at most 256 characters")
        String routeDefinition,

        @Schema(description = "HTTP method", example = "POST")
        @NotNull(message = "Method is required")
        HttpMethod method,

        @Schema(description = "Description of the endpoint", example = "Creates a new invoice", maxLength = 500)
        @Size(max = 500, message = "Description must have at most 500 characters")
        String description,

        @Schema(description = "Example request payload")
        String requestExample,

        @Schema(description = "Example response payload")
        String responseExample,

        @Schema(description = "Whether this endpoint requires authentication", example = "true")
        Boolean requiresAuth
) {
}
