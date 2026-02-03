# Plan: Remove RabbitMQ and keep Pub/Sub only

## Goal
Remove all RabbitMQ dependencies, configuration, and usage, keeping GCP Pub/Sub (and emulator) as the only messaging option.

## Steps
1. Remove RabbitMQ dependency from pom.xml.
2. Delete RabbitMQ configuration class and RabbitMQ publisher implementation.
3. Remove RabbitMQ properties and test exclusions from application properties.
4. Remove RabbitMQ compose service and related documentation mentions.
5. Delete RabbitMQ-specific unit tests and adjust any related references.
6. Verify no remaining RabbitMQ references in source or tests.
