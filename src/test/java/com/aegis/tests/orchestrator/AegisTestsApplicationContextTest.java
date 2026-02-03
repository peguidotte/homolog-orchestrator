package com.aegis.tests.orchestrator;

import com.aegis.tests.orchestrator.shared.config.TestMessagingConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestMessagingConfiguration.class)
class AegisTestsApplicationContextTest {

    @Test
    void contextLoads() {
        // If the Spring ApplicationContext fails to start, this test will fail.
    }
}
