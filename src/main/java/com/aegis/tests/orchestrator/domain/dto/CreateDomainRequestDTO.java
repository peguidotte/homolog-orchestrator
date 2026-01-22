package com.aegis.tests.orchestrator.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating a Domain")
public record CreateDomainRequestDTO(

        @Schema(description = "Domain name", example = "Invoices", maxLength = 128)
        @NotBlank(message = "Name is required")
        @Size(max = 128, message = "Name must have at most 128 characters")
        String name,

        @Schema(description = "Domain description", example = "Invoice management endpoints", maxLength = 1024)
        @Size(max = 1024, message = "Description must have at most 1024 characters")
        String description
) {
}
