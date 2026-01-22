package com.aegis.tests.orchestrator.baseurl.dto;

import com.aegis.tests.orchestrator.baseurl.BaseUrl;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response payload representing a BaseUrl")
public record BaseUrlResponseDTO(

        @Schema(description = "BaseUrl ID", example = "1")
        Long id,

        @Schema(description = "TestProject ID", example = "1")
        Long testProjectId,

        @Schema(description = "Environment ID", example = "1")
        Long environmentId,

        @Schema(description = "Unique identifier", example = "INVOICES_API")
        String identifier,

        @Schema(description = "The complete base URL", example = "https://api.example.com/v1")
        String url,

        @Schema(description = "Description", example = "Invoices API base URL")
        String description,

        @Schema(description = "Creation timestamp")
        Instant createdAt,

        @Schema(description = "User who created")
        String createdBy
) {
    public static BaseUrlResponseDTO fromEntity(BaseUrl entity) {
        return new BaseUrlResponseDTO(
                entity.getId(),
                entity.getTestProject().getId(),
                entity.getEnvironment().getId(),
                entity.getIdentifier(),
                entity.getUrl(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
}
