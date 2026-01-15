package com.aegis.homolog.orchestrator.exception;

import com.aegis.homolog.orchestrator.model.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Handler global para exceções da API.
 * Garante padronização das respostas de erro conforme api_response_standards.md
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handler para erros de validação de campo (Bean Validation).
     * Retorna 400 Bad Request com lista de todos os erros de validação.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDTO>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorResponseDTO> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ErrorResponseDTO.of(
                        "INVALID_FIELD_LENGTH",
                        fieldError.getDefaultMessage(),
                        fieldError.getField()
                ))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handler para limite de TestProjects excedido.
     * RN10.01.2 → 422 Unprocessable Entity
     */
    @ExceptionHandler(TestProjectLimitReachedException.class)
    public ResponseEntity<List<ErrorResponseDTO>> handleTestProjectLimitReached(TestProjectLimitReachedException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.of(
                ex.getErrorCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(List.of(error));
    }

    /**
     * Handler para nome de TestProject duplicado.
     * RN10.01.3 → 409 Conflict
     */
    @ExceptionHandler(TestProjectNameAlreadyExistsException.class)
    public ResponseEntity<List<ErrorResponseDTO>> handleTestProjectNameAlreadyExists(TestProjectNameAlreadyExistsException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.of(
                ex.getErrorCode(),
                ex.getMessage(),
                "name"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of(error));
    }
}

