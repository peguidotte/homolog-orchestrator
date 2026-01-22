package com.aegis.tests.orchestrator.environment;

import com.aegis.tests.orchestrator.environment.dto.CreateEnvironmentRequestDTO;
import com.aegis.tests.orchestrator.environment.dto.EnvironmentResponseDTO;
import com.aegis.tests.orchestrator.testproject.TestProject;
import com.aegis.tests.orchestrator.testproject.TestProjectRepository;
import com.aegis.tests.orchestrator.testproject.exception.TestProjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for Environment domain operations.
 */
@Service
public class EnvironmentService {

    private static final String DEFAULT_ENVIRONMENT_NAME = "Default";
    private static final String DEFAULT_ENVIRONMENT_DESCRIPTION = "Default environment created automatically";

    private final EnvironmentRepository environmentRepository;
    private final TestProjectRepository testProjectRepository;

    public EnvironmentService(EnvironmentRepository environmentRepository,
                              TestProjectRepository testProjectRepository) {
        this.environmentRepository = environmentRepository;
        this.testProjectRepository = testProjectRepository;
    }

    /**
     * Creates a new Environment for a TestProject.
     */
    @Transactional
    public EnvironmentResponseDTO create(CreateEnvironmentRequestDTO request, String userId) {
        TestProject testProject = testProjectRepository.findById(request.testProjectId())
                .orElseThrow(() -> new TestProjectNotFoundException(request.testProjectId()));

        Environment environment = Environment.builder()
                .testProject(testProject)
                .name(request.name())
                .description(request.description())
                .isDefault(request.isDefault() != null ? request.isDefault() : false)
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();

        Environment saved = environmentRepository.save(environment);
        return EnvironmentResponseDTO.fromEntity(saved);
    }

    /**
     * Creates the default environment for a TestProject.
     * Called automatically when a TestProject is created.
     *
     * @param testProject The TestProject to create the default environment for
     * @param userId The user creating the environment
     * @return The created default Environment
     */
    public Environment createDefault(TestProject testProject, String userId) {
        Environment defaultEnvironment = Environment.builder()
                .testProject(testProject)
                .name(DEFAULT_ENVIRONMENT_NAME)
                .description(DEFAULT_ENVIRONMENT_DESCRIPTION)
                .isDefault(true)
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();

        return environmentRepository.save(defaultEnvironment);
    }
}

