package com.aegis.homolog.orchestrator.services;

import com.aegis.homolog.orchestrator.exception.TestProjectLimitReachedException;
import com.aegis.homolog.orchestrator.exception.TestProjectNameAlreadyExistsException;
import com.aegis.homolog.orchestrator.model.dto.CreateTestProjectRequestDTO;
import com.aegis.homolog.orchestrator.model.dto.TestProjectResponseDTO;
import com.aegis.homolog.orchestrator.model.entity.Environment;
import com.aegis.homolog.orchestrator.model.entity.TestProject;
import com.aegis.homolog.orchestrator.repository.EnvironmentRepository;
import com.aegis.homolog.orchestrator.repository.TestProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestProjectService {

    private static final int MAX_TEST_PROJECTS_PER_PROJECT = 1; // MVP limit

    private final TestProjectRepository testProjectRepository;
    private final EnvironmentRepository environmentRepository;

    public TestProjectService(TestProjectRepository testProjectRepository,
                              EnvironmentRepository environmentRepository) {
        this.testProjectRepository = testProjectRepository;
        this.environmentRepository = environmentRepository;
    }

    /**
     * Cria um novo TestProject para o projeto especificado.
     *
     * @param projectId ID do Projeto Core
     * @param request DTO com dados do TestProject
     * @param userId ID do usuário que está criando
     * @return DTO com os dados do TestProject criado
     * @throws TestProjectLimitReachedException se o limite de TestProjects for atingido (RN10.01.2)
     * @throws TestProjectNameAlreadyExistsException se já existir um TestProject com o mesmo nome (RN10.01.3)
     */
    @Transactional
    public TestProjectResponseDTO create(Long projectId, CreateTestProjectRequestDTO request, String userId) {
        // RN10.01.2: Validar limite de TestProjects por projeto
        validateTestProjectLimit(projectId);

        // RN10.01.3: Validar unicidade do nome dentro do projeto
        validateNameUniqueness(projectId, request.name());

        // Criar e persistir o TestProject
        TestProject testProject = buildTestProject(projectId, request, userId);
        TestProject savedProject = testProjectRepository.save(testProject);

        // Criar Environment "Default" automaticamente
        createDefaultEnvironment(savedProject, userId);

        return TestProjectResponseDTO.fromEntity(savedProject);
    }

    private void validateTestProjectLimit(Long projectId) {
        var existingProjects = testProjectRepository.findByProjectId(projectId);
        if (!existingProjects.isEmpty()) {
            throw new TestProjectLimitReachedException(projectId);
        }
    }

    private void validateNameUniqueness(Long projectId, String name) {
        testProjectRepository.findByProjectIdAndName(projectId, name)
                .ifPresent(existing -> {
                    throw new TestProjectNameAlreadyExistsException(name, projectId);
                });
    }

    private TestProject buildTestProject(Long projectId, CreateTestProjectRequestDTO request, String userId) {
        return TestProject.builder()
                .projectId(projectId)
                .name(request.name())
                .description(request.description())
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();
    }

    private void createDefaultEnvironment(TestProject testProject, String userId) {
        Environment defaultEnvironment = Environment.builder()
                .testProject(testProject)
                .name("Default")
                .description("Default environment created automatically")
                .isDefault(true)
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();

        environmentRepository.save(defaultEnvironment);
    }
}

