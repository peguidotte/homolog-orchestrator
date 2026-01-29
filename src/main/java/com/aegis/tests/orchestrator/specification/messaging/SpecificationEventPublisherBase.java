package com.aegis.tests.orchestrator.specification.messaging;

import com.aegis.tests.orchestrator.specification.dto.event.SpecificationCreatedEvent;

public abstract class SpecificationEventPublisherBase {

    /**
     * Publishes a SpecificationCreatedEvent.
     *
     * @param event the specification created event
     */
    public abstract void publishSpecificationCreated(SpecificationCreatedEvent event);
}

