package com.aegis.tests.orchestrator.testproject.exception;

import com.aegis.tests.orchestrator.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class TestProjectNotFoundException extends BusinessException {
    private static final String ERROR_CODE = "TEST_PROJECT_NOT_FOUND";

    public TestProjectNotFoundException(Long testProjectId) {
        super(
                String.format("TestProject with ID %d not found", testProjectId),
                ERROR_CODE,
                HttpStatus.NOT_FOUND
        );
    }
}

