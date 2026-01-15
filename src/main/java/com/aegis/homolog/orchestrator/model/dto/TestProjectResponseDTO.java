package com.aegis.homolog.orchestrator.model.dto;

import com.aegis.homolog.orchestrator.model.entity.TestProject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response payload representing a TestProject")
public record TestProjectResponseDTO(

        @Schema(description = "ID único do TestProject", example = "500")
        Long id,

        @Schema(description = "Nome identificador do módulo de teste", example = "Suíte de Testes - Recebíveis")
        String name,

        @Schema(description = "ID do Projeto Core associado", example = "10")
        Long projectId,

        @Schema(description = "Descrição do escopo do módulo de teste", example = "Testes de integração da API de Duplicatas")
        String description,

        @Schema(description = "Data de criação do TestProject", example = "2026-01-12T14:00:00Z")
        Instant createdAt,

        @Schema(description = "Usuário que criou o TestProject", example = "Pedro Guidotte")
        String createdBy
) {

    public static TestProjectResponseDTO fromEntity(TestProject entity) {
        return new TestProjectResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getProjectId(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
}

