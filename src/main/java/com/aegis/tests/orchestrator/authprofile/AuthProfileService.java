package com.aegis.tests.orchestrator.authprofile;

import com.aegis.tests.orchestrator.authprofile.dto.AuthProfileResponseDTO;
import com.aegis.tests.orchestrator.authprofile.dto.CreateAuthProfileRequestDTO;
import com.aegis.tests.orchestrator.environment.Environment;
import com.aegis.tests.orchestrator.environment.EnvironmentRepository;
import com.aegis.tests.orchestrator.environment.exception.EnvironmentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for AuthProfile operations.
 * Simple CRUD for supporting Specification creation.
 */
@Service
public class AuthProfileService {

    private final AuthProfileRepository authProfileRepository;
    private final EnvironmentRepository environmentRepository;

    public AuthProfileService(AuthProfileRepository authProfileRepository,
                              EnvironmentRepository environmentRepository) {
        this.authProfileRepository = authProfileRepository;
        this.environmentRepository = environmentRepository;
    }

    @Transactional
    public AuthProfileResponseDTO create(CreateAuthProfileRequestDTO request, String userId) {
        Environment environment = environmentRepository.findById(request.environmentId())
                .orElseThrow(() -> new EnvironmentNotFoundException(request.environmentId()));

        AuthCredentials credentials = buildCredentials(request);

        AuthProfile authProfile = AuthProfile.builder()
                .environment(environment)
                .name(request.name())
                .credentials(credentials)
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();

        AuthProfile saved = authProfileRepository.save(authProfile);
        return AuthProfileResponseDTO.fromEntity(saved);
    }

    private AuthCredentials buildCredentials(CreateAuthProfileRequestDTO request) {
        return switch (request.authType().toUpperCase()) {
            case "BEARER_TOKEN" -> BearerTokenCredentials.builder()
                    .token(request.token())
                    .build();
            case "BASIC_AUTH" -> BasicAuthCredentials.builder()
                    .username(request.username())
                    .password(request.password())
                    .build();
            default -> throw new IllegalArgumentException("Unknown auth type: " + request.authType());
        };
    }
}
