package com.aegis.homolog.orchestrator.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Schema(description = "Standard error response object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(

        @Schema(description = "Internal standardized error code", example = "ERROR_CODE_HERE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NonNull String errorCode,

        @Schema(description = "User-friendly error message", example = "User-friendly error message here", requiredMode = Schema.RequiredMode.REQUIRED)
        @NonNull String message,

        @Schema(description = "Field name that caused the error (when applicable)", example = "Field name if applicable")
        @Nullable String field
) {

    public static ErrorResponseDTO of(String errorCode, String message) {
        return new ErrorResponseDTO(errorCode, message, null);
    }

    public static ErrorResponseDTO of(String errorCode, String message, String field) {
        return new ErrorResponseDTO(errorCode, message, field);
    }
}

