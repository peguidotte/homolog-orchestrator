package com.aegis.homolog.orchestrator.controller;

import com.aegis.homolog.orchestrator.model.dto.CreateTestProjectRequestDTO;
import com.aegis.homolog.orchestrator.model.dto.ErrorResponseDTO;
import com.aegis.homolog.orchestrator.model.dto.TestProjectResponseDTO;
import com.aegis.homolog.orchestrator.services.TestProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciamento de TestProjects.
 * Implementa os endpoints da HU10_00.
 */
@RestController
@RequestMapping("/v1/projects/{projectId}/test-projects")
@Tag(name = "Test Projects", description = "Endpoints para gerenciamento de projetos de teste")
public class TestProjectController {

    private final TestProjectService testProjectService;

    public TestProjectController(TestProjectService testProjectService) {
        this.testProjectService = testProjectService;
    }

    /**
     * Cria um novo TestProject para o projeto especificado.
     * HU10_00: Criar Projeto de Testes Automatizados
     */
    @Operation(
            summary = "Criar TestProject",
            description = """
                    Cria a entidade raiz do Aegis Tests. Este TestProject servirá como o container 
                    para Environments, API Calls e Specifications. Ele herda as permissões de membros 
                    do Projeto Core.
                    
                    **Regras de Negócio:**
                    - RN10.01.2: Cada Projeto Core só pode ter 1 TestProject (MVP)
                    - RN10.01.3: Não pode existir dois TestProjects com mesmo nome no mesmo projeto
                    
                    **Comportamento Automático:**
                    - Cria um Environment "Default" automaticamente
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "TestProject criado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TestProjectResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação de campo",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito - Nome do TestProject já existe no projeto",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Regra de negócio violada - Limite de TestProjects atingido",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            )
    })
    @PostMapping
    public ResponseEntity<TestProjectResponseDTO> create(
            @Parameter(description = "ID do Projeto Core", required = true, example = "10")
            @PathVariable Long projectId,

            @Valid @RequestBody CreateTestProjectRequestDTO request
    ) {
        // TODO: Extrair userId do token JWT (RN10.01.1)
        // Por enquanto, usando valor fixo para MVP
        String userId = "system-user";

        TestProjectResponseDTO response = testProjectService.create(projectId, request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

