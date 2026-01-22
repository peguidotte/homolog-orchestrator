package com.aegis.tests.orchestrator.baseurl;

import com.aegis.tests.orchestrator.baseurl.dto.BaseUrlResponseDTO;
import com.aegis.tests.orchestrator.baseurl.dto.CreateBaseUrlRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/base-urls")
@Tag(name = "Base URLs", description = "Base URL management endpoints")
public class BaseUrlController {

    private final BaseUrlService baseUrlService;

    public BaseUrlController(BaseUrlService baseUrlService) {
        this.baseUrlService = baseUrlService;
    }

    @Operation(summary = "Create BaseUrl", description = "Creates a new base URL for a TestProject/Environment")
    @PostMapping
    public ResponseEntity<BaseUrlResponseDTO> create(@Valid @RequestBody CreateBaseUrlRequestDTO request) {
        String userId = "system-user"; // TODO: Extract from JWT
        return ResponseEntity.status(HttpStatus.CREATED).body(baseUrlService.create(request, userId));
    }
}
