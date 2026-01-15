package com.aegis.homolog.orchestrator.exception;

/**
 * Exceção lançada quando o limite de TestProjects por projeto é atingido.
 * RN10.01.2: Cada Projeto Core só pode ter 1 TestProject (MVP).
 */
public class TestProjectLimitReachedException extends RuntimeException {

    private static final String ERROR_CODE = "TEST_PROJECT_LIMIT_REACHED";

    public TestProjectLimitReachedException(Long projectId) {
        super(String.format("Project %d has already reached the maximum limit of TestProjects", projectId));
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}

