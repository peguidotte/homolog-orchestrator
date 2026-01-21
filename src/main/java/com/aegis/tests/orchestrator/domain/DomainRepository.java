package com.aegis.tests.orchestrator.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {

    List<Domain> findByNameContainingIgnoreCase(String fragment);
}
