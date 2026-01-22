package com.aegis.tests.orchestrator.domain;

import com.aegis.tests.orchestrator.domain.dto.CreateDomainRequestDTO;
import com.aegis.tests.orchestrator.domain.dto.DomainResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Domain operations.
 * Simple CRUD for supporting Specification creation.
 */
@Service
public class DomainService {

    private final DomainRepository domainRepository;

    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Transactional
    public DomainResponseDTO create(CreateDomainRequestDTO request, String userId) {
        Domain domain = Domain.builder()
                .name(request.name())
                .description(request.description())
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();

        Domain saved = domainRepository.save(domain);
        return DomainResponseDTO.fromEntity(saved);
    }
}
