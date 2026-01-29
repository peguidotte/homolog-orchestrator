package com.aegis.tests.orchestrator.specification.dto.event;

import com.aegis.tests.orchestrator.authprofile.AuthProfile;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AuthProfile data for event consumption by external agents.
 * Note: Sensitive credentials are NOT included for security reasons.
 */
@Schema(description = "Authentication profile context for AI agent (no credentials)")
public record AuthProfileContext(

        @Schema(description = "Authentication profile ID", example = "20")
        Long id,

        @Schema(description = "Profile name", example = "JWT Admin Token")
        String name,

        @Schema(description = "Authentication type", example = "BEARER")
        String authType
) {
    public static AuthProfileContext fromEntity(AuthProfile entity) {
        if (entity == null) return null;
        return new AuthProfileContext(
                entity.getId(),
                entity.getName(),
                entity.getAuthType()
        );
    }
}
