package com.aegis.tests.orchestrator.apicall;

import com.aegis.tests.orchestrator.apicall.dto.ApiCallResponseDTO;
import com.aegis.tests.orchestrator.apicall.dto.CreateApiCallRequestDTO;
import com.aegis.tests.orchestrator.baseurl.BaseUrl;
import com.aegis.tests.orchestrator.baseurl.BaseUrlRepository;
import com.aegis.tests.orchestrator.baseurl.exception.BaseUrlNotFoundException;
import com.aegis.tests.orchestrator.domain.Domain;
import com.aegis.tests.orchestrator.domain.DomainRepository;
import com.aegis.tests.orchestrator.domain.exception.DomainNotFoundException;
import com.aegis.tests.orchestrator.testproject.TestProject;
import com.aegis.tests.orchestrator.testproject.TestProjectRepository;
import com.aegis.tests.orchestrator.testproject.exception.TestProjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ApiCall operations.
 * Simple CRUD for supporting Specification creation.
 */
@Service
public class ApiCallService {

    private final ApiCallRepository apiCallRepository;
    private final TestProjectRepository testProjectRepository;
    private final BaseUrlRepository baseUrlRepository;
    private final DomainRepository domainRepository;

    public ApiCallService(ApiCallRepository apiCallRepository,
                          TestProjectRepository testProjectRepository,
                          BaseUrlRepository baseUrlRepository,
                          DomainRepository domainRepository) {
        this.apiCallRepository = apiCallRepository;
        this.testProjectRepository = testProjectRepository;
        this.baseUrlRepository = baseUrlRepository;
        this.domainRepository = domainRepository;
    }

    @Transactional
    public ApiCallResponseDTO create(CreateApiCallRequestDTO request, String userId) {
        TestProject testProject = testProjectRepository.findById(request.testProjectId())
                .orElseThrow(() -> new TestProjectNotFoundException(request.testProjectId()));

        BaseUrl baseUrl = baseUrlRepository.findById(request.baseUrlId())
                .orElseThrow(() -> new BaseUrlNotFoundException(request.baseUrlId()));

        Domain domain = null;
        if (request.domainId() != null) {
            domain = domainRepository.findById(request.domainId())
                    .orElseThrow(() -> new DomainNotFoundException(request.domainId()));
        }

        ApiCall apiCall = ApiCall.builder()
                .project(testProject)
                .baseUrl(baseUrl)
                .domain(domain)
                .routeDefinition(request.routeDefinition())
                .method(request.method())
                .description(request.description())
                .requestExample(request.requestExample())
                .responseExample(request.responseExample())
                .requiresAuth(request.requiresAuth() != null ? request.requiresAuth() : true)
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();

        ApiCall saved = apiCallRepository.save(apiCall);
        return ApiCallResponseDTO.fromEntity(saved);
    }
}
