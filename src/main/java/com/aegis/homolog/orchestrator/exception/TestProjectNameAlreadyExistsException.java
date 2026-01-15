package com.aegis.homolog.orchestrator.exception;

/**
 * Exceção lançada quando já existe um TestProject com o mesmo nome no projeto.
 * RN10.01.3: Não deve existir dois TestProjects com mesmo nome no mesmo projeto.
 */
public class TestProjectNameAlreadyExistsException extends RuntimeException {

    private static final String ERROR_CODE = "TEST_PROJECT_NAME_ALREADY_EXISTS";

    public TestProjectNameAlreadyExistsException(String name, Long projectId) {
        super(String.format("A TestProject with name '%s' already exists in project %d", name, projectId));
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}

