package com.aegis.tests.orchestrator.domain;

import com.aegis.tests.orchestrator.domain.dto.CreateDomainRequestDTO;
import com.aegis.tests.orchestrator.domain.dto.DomainResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/domains")
@Tag(name = "Domains", description = "Domain management endpoints")
public class DomainController {

    private final DomainService domainService;

    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @Operation(summary = "Create Domain", description = "Creates a new Domain for semantic grouping")
    @PostMapping
    public ResponseEntity<DomainResponseDTO> create(@Valid @RequestBody CreateDomainRequestDTO request) {
        String userId = "system-user"; // TODO: Extract from JWT
        return ResponseEntity.status(HttpStatus.CREATED).body(domainService.create(request, userId));
    }
}
