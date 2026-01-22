package com.aegis.tests.orchestrator.environment.dto;

import com.aegis.tests.orchestrator.environment.Environment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response payload representing an Environment")
public record EnvironmentResponseDTO(

        @Schema(description = "Environment ID", example = "1")
        Long id,

        @Schema(description = "TestProject ID", example = "1")
        Long testProjectId,

        @Schema(description = "Environment name", example = "Staging")
        String name,

        @Schema(description = "Environment description", example = "Staging environment")
        String description,

        @Schema(description = "Whether this is the default environment", example = "false")
        Boolean isDefault,

        @Schema(description = "Creation timestamp")
        Instant createdAt,

        @Schema(description = "User who created")
        String createdBy
) {
    public static EnvironmentResponseDTO fromEntity(Environment entity) {
        return new EnvironmentResponseDTO(
                entity.getId(),
                entity.getTestProject().getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsDefault(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
}
