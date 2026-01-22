package com.aegis.tests.orchestrator.baseurl;

import com.aegis.tests.orchestrator.baseurl.dto.BaseUrlResponseDTO;
import com.aegis.tests.orchestrator.baseurl.dto.CreateBaseUrlRequestDTO;
import com.aegis.tests.orchestrator.environment.Environment;
import com.aegis.tests.orchestrator.environment.EnvironmentRepository;
import com.aegis.tests.orchestrator.environment.exception.EnvironmentNotFoundException;
import com.aegis.tests.orchestrator.testproject.TestProject;
import com.aegis.tests.orchestrator.testproject.TestProjectRepository;
import com.aegis.tests.orchestrator.testproject.exception.TestProjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for BaseUrl operations.
 * Simple CRUD for supporting ApiCall creation.
 */
@Service
public class BaseUrlService {

    private final BaseUrlRepository baseUrlRepository;
    private final TestProjectRepository testProjectRepository;
    private final EnvironmentRepository environmentRepository;

    public BaseUrlService(BaseUrlRepository baseUrlRepository,
                          TestProjectRepository testProjectRepository,
                          EnvironmentRepository environmentRepository) {
        this.baseUrlRepository = baseUrlRepository;
        this.testProjectRepository = testProjectRepository;
        this.environmentRepository = environmentRepository;
    }

    @Transactional
    public BaseUrlResponseDTO create(CreateBaseUrlRequestDTO request, String userId) {
        TestProject testProject = testProjectRepository.findById(request.testProjectId())
                .orElseThrow(() -> new TestProjectNotFoundException(request.testProjectId()));

        Environment environment = environmentRepository.findById(request.environmentId())
                .orElseThrow(() -> new EnvironmentNotFoundException(request.environmentId()));

        BaseUrl baseUrl = BaseUrl.builder()
                .testProject(testProject)
                .environment(environment)
                .identifier(request.identifier())
                .url(request.url())
                .description(request.description())
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();

        BaseUrl saved = baseUrlRepository.save(baseUrl);
        return BaseUrlResponseDTO.fromEntity(saved);
    }
}
