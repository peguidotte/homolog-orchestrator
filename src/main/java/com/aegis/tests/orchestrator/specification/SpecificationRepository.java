package com.aegis.tests.orchestrator.specification;

import com.aegis.tests.orchestrator.shared.model.enums.HttpMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Long> {
    boolean existsByMethodAndPathAndEnvironmentId(HttpMethod method, String path, Long environmentId);
}

