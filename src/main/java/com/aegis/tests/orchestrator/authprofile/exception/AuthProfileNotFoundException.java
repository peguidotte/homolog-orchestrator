package com.aegis.tests.orchestrator.authprofile.exception;

import com.aegis.tests.orchestrator.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AuthProfileNotFoundException extends BusinessException {
    private static final String ERROR_CODE = "AUTH_PROFILE_NOT_FOUND";

    public AuthProfileNotFoundException(Long authProfileId) {
        super(
                String.format("AuthProfile with ID %d not found", authProfileId),
                ERROR_CODE,
                HttpStatus.NOT_FOUND
        );
    }
}

