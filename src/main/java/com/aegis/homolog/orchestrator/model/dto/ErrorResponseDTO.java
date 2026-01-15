package com.aegis.homolog.orchestrator.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard error response object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(

        @Schema(
                description = "Código interno e padronizado do erro",
                example = "INVALID_FIELD_LENGTH"
        )
        String errorCode,

        @Schema(
                description = "Mensagem amigável que pode ser exibida ao usuário",
                example = "Name must have at most 255 characters"
        )
        String message,

        @Schema(
                description = "Nome do campo que causou o erro (quando aplicável)",
                example = "name"
        )
        String field
) {

    public static ErrorResponseDTO of(String errorCode, String message) {
        return new ErrorResponseDTO(errorCode, message, null);
    }

    public static ErrorResponseDTO of(String errorCode, String message, String field) {
        return new ErrorResponseDTO(errorCode, message, field);
    }
}

