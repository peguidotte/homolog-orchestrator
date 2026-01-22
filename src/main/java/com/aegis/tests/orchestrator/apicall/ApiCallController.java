package com.aegis.tests.orchestrator.apicall;

import com.aegis.tests.orchestrator.apicall.dto.ApiCallResponseDTO;
import com.aegis.tests.orchestrator.apicall.dto.CreateApiCallRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api-calls")
@Tag(name = "API Calls", description = "API Call catalog management endpoints")
public class ApiCallController {

    private final ApiCallService apiCallService;

    public ApiCallController(ApiCallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    @Operation(summary = "Create ApiCall", description = "Creates a new API call in the endpoint catalog")
    @PostMapping
    public ResponseEntity<ApiCallResponseDTO> create(@Valid @RequestBody CreateApiCallRequestDTO request) {
        String userId = "system-user"; // TODO: Extract from JWT
        return ResponseEntity.status(HttpStatus.CREATED).body(apiCallService.create(request, userId));
    }
}
