package com.aegis.tests.orchestrator.environment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating an Environment")
public record CreateEnvironmentRequestDTO(

        @Schema(description = "TestProject ID", example = "1")
        @NotNull(message = "TestProjectId is required")
        Long testProjectId,

        @Schema(description = "Environment name", example = "Staging", maxLength = 100)
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must have at most 100 characters")
        String name,

        @Schema(description = "Environment description", example = "Staging environment", maxLength = 500)
        @Size(max = 500, message = "Description must have at most 500 characters")
        String description,

        @Schema(description = "Whether this is the default environment", example = "false")
        Boolean isDefault
) {
}
