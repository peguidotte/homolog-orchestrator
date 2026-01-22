package com.aegis.tests.orchestrator.environment;

import com.aegis.tests.orchestrator.environment.dto.CreateEnvironmentRequestDTO;
import com.aegis.tests.orchestrator.environment.dto.EnvironmentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/environments")
@Tag(name = "Environments", description = "Environment management endpoints")
public class EnvironmentController {

    private final EnvironmentService environmentService;

    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @Operation(summary = "Create Environment", description = "Creates a new Environment for a TestProject")
    @PostMapping
    public ResponseEntity<EnvironmentResponseDTO> create(@Valid @RequestBody CreateEnvironmentRequestDTO request) {
        String userId = "system-user"; // TODO: Extract from JWT
        return ResponseEntity.status(HttpStatus.CREATED).body(environmentService.create(request, userId));
    }
}
