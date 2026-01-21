package com.aegis.tests.orchestrator.specification.exception;

import com.aegis.tests.orchestrator.shared.exception.BusinessException;
import com.aegis.tests.orchestrator.shared.model.enums.HttpMethod;
import org.springframework.http.HttpStatus;

public class SpecificationAlreadyExistsException extends BusinessException {
    private static final String ERROR_CODE = "SPECIFICATION_ALREADY_EXISTS";

    public SpecificationAlreadyExistsException(HttpMethod method, String path, Long environmentId) {
        super(
                String.format("Specification with method %s and path '%s' already exists in Environment %d",
                        method, path, environmentId),
                ERROR_CODE,
                HttpStatus.CONFLICT
        );
    }
}

