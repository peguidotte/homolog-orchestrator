package com.aegis.tests.orchestrator.specification.dto.event;

import com.aegis.tests.orchestrator.domain.Domain;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Domain data for event consumption by external agents.
 */
@Schema(description = "Domain context for AI agent")
public record DomainContext(

        @Schema(description = "Domain ID", example = "15")
        Long id,

        @Schema(description = "Domain name", example = "User")
        String name,

        @Schema(description = "Domain description", example = "User management domain")
        String description
) {
    public static DomainContext fromEntity(Domain entity) {
        if (entity == null) return null;
        return new DomainContext(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
