package com.aegis.tests.orchestrator.specification.dto.event;

import com.aegis.tests.orchestrator.testproject.TestProject;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * TestProject data for event consumption by external agents.
 */
@Schema(description = "Test project context for AI agent")
public record TestProjectContext(
        @Schema(description = "Project Id", example = "121")
        Long id,

        @Schema(description = "Project name", example = "E-Commerce API Tests")
        String name,

        @Schema(description = "Project description", example = "Automated tests for e-commerce platform")
        String description
) {
    public static TestProjectContext fromEntity(TestProject entity) {
        if (entity == null) return null;
        return new TestProjectContext(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
