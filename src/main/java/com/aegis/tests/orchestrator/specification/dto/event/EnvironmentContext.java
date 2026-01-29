package com.aegis.tests.orchestrator.specification.dto.event;

import com.aegis.tests.orchestrator.environment.Environment;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Environment data for event consumption by external agents.
 * Contains actual values instead of IDs.
 */
@Schema(description = "Environment context for AI agent")
public record EnvironmentContext(

        @Schema(description = "Environment ID", example = "10")
        Long id,

        @Schema(description = "Environment name", example = "Staging")
        String name,

        @Schema(description = "Environment description", example = "Staging environment for QA")
        String description,

        @Schema(description = "Whether this is the default environment", example = "true")
        Boolean isDefault
) {
    public static EnvironmentContext fromEntity(Environment entity) {
        if (entity == null) return null;
        return new EnvironmentContext(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsDefault()
        );
    }
}
