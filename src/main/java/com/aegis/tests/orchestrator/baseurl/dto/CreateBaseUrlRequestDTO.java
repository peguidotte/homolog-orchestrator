package com.aegis.tests.orchestrator.baseurl.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating a BaseUrl")
public record CreateBaseUrlRequestDTO(

        @Schema(description = "TestProject ID", example = "1")
        @NotNull(message = "TestProjectId is required")
        Long testProjectId,

        @Schema(description = "Environment ID", example = "1")
        @NotNull(message = "EnvironmentId is required")
        Long environmentId,

        @Schema(description = "Unique identifier for this base URL", example = "INVOICES_API", maxLength = 100)
        @NotBlank(message = "Identifier is required")
        @Size(max = 100, message = "Identifier must have at most 100 characters")
        String identifier,

        @Schema(description = "The complete base URL", example = "https://api.example.com/v1", maxLength = 500)
        @NotBlank(message = "Url is required")
        @Size(max = 500, message = "Url must have at most 500 characters")
        String url,

        @Schema(description = "Description of this base URL", example = "Invoices API base URL", maxLength = 500)
        @Size(max = 500, message = "Description must have at most 500 characters")
        String description
) {
}
