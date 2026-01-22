package com.aegis.tests.orchestrator.authprofile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating an AuthProfile")
public record CreateAuthProfileRequestDTO(

        @Schema(description = "Environment ID", example = "1")
        @NotNull(message = "EnvironmentId is required")
        Long environmentId,

        @Schema(description = "AuthProfile name", example = "Admin Token", maxLength = 100)
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must have at most 100 characters")
        String name,

        @Schema(description = "Auth type: BEARER_TOKEN or BASIC_AUTH", example = "BEARER_TOKEN")
        @NotBlank(message = "AuthType is required")
        String authType,

        @Schema(description = "Token for BEARER_TOKEN auth type", example = "eyJhbGciOiJIUzI1NiIs...")
        String token,

        @Schema(description = "Username for BASIC_AUTH auth type", example = "admin")
        String username,

        @Schema(description = "Password for BASIC_AUTH auth type", example = "secret")
        String password
) {
}
