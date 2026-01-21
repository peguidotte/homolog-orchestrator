package com.aegis.tests.orchestrator.authprofile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthProfileRepository extends JpaRepository<AuthProfile, Long> {
    boolean existsByIdAndEnvironmentId(Long id, Long environmentId);
}

