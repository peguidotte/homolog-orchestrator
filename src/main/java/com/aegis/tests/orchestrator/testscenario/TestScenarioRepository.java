package com.aegis.tests.orchestrator.testscenario;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestScenarioRepository extends JpaRepository<TestScenario, Long> {

    List<TestScenario> findByProjectIdAndTagIdsContaining(Long projectId, Long tagId);
}
