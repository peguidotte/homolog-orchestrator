package com.aegis.tests.orchestrator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AegisTestsApplicationContextTest {

    @Test
    void contextLoads() {
        // If the Spring ApplicationContext fails to start, this test will fail.
    }
}
