package com.aegis.tests.orchestrator.domain.dto;

import com.aegis.tests.orchestrator.domain.Domain;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response payload representing a Domain")
public record DomainResponseDTO(

        @Schema(description = "Domain ID", example = "1")
        Long id,

        @Schema(description = "Domain name", example = "Invoices")
        String name,

        @Schema(description = "Domain description", example = "Invoice management endpoints")
        String description,

        @Schema(description = "Creation timestamp")
        Instant createdAt,

        @Schema(description = "User who created")
        String createdBy
) {
    public static DomainResponseDTO fromEntity(Domain entity) {
        return new DomainResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
}
