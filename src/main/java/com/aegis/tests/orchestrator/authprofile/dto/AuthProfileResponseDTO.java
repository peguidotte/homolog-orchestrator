package com.aegis.tests.orchestrator.authprofile.dto;

import com.aegis.tests.orchestrator.authprofile.AuthProfile;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response payload representing an AuthProfile")
public record AuthProfileResponseDTO(

        @Schema(description = "AuthProfile ID", example = "1")
        Long id,

        @Schema(description = "Environment ID", example = "1")
        Long environmentId,

        @Schema(description = "AuthProfile name", example = "Admin Token")
        String name,

        @Schema(description = "Auth type", example = "BEARER_TOKEN")
        String authType,

        @Schema(description = "Creation timestamp")
        Instant createdAt,

        @Schema(description = "User who created")
        String createdBy
) {
    public static AuthProfileResponseDTO fromEntity(AuthProfile entity) {
        return new AuthProfileResponseDTO(
                entity.getId(),
                entity.getEnvironment().getId(),
                entity.getName(),
                entity.getAuthType(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
}
