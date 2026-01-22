package com.aegis.tests.orchestrator.authprofile;

import com.aegis.tests.orchestrator.authprofile.dto.AuthProfileResponseDTO;
import com.aegis.tests.orchestrator.authprofile.dto.CreateAuthProfileRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth-profiles")
@Tag(name = "Auth Profiles", description = "Authentication profile management endpoints")
public class AuthProfileController {

    private final AuthProfileService authProfileService;

    public AuthProfileController(AuthProfileService authProfileService) {
        this.authProfileService = authProfileService;
    }

    @Operation(summary = "Create AuthProfile", description = "Creates a new authentication profile for an Environment")
    @PostMapping
    public ResponseEntity<AuthProfileResponseDTO> create(@Valid @RequestBody CreateAuthProfileRequestDTO request) {
        String userId = "system-user"; // TODO: Extract from JWT
        return ResponseEntity.status(HttpStatus.CREATED).body(authProfileService.create(request, userId));
    }
}
