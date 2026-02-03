package com.aegis.tests.orchestrator.shared.config;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

/**
 * Test configuration that provides a mocked PubSubTemplate bean for integration tests.
 * This allows the Spring context to load successfully in test environment where Pub/Sub
 * is disabled, while still testing real PubSubSpecificationEventPublisher behavior.
 */
@TestConfiguration
public class TestMessagingConfiguration {

    /**
     * Provides a mocked PubSubTemplate for test context.
     * This bean is used by PubSubSpecificationEventPublisher in integration tests.
     *
     * @return mocked PubSubTemplate instance
     */
    @Bean
    public PubSubTemplate pubSubTemplate() {
        return mock(PubSubTemplate.class);
    }
}
