package com.aegis.homolog.orchestrator.config;

import com.aegis.homolog.orchestrator.services.EncryptionService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter
@Component
public class EncryptedStringConverter implements AttributeConverter<String, String> {

    private static EncryptionService encryptionService;

    public EncryptedStringConverter(EncryptionService encryptionService) {
        EncryptedStringConverter.encryptionService = encryptionService;
    }

    public EncryptedStringConverter() {
        // Required by JPA
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        if (encryptionService == null) {
            // Fallback for cases where Spring context is not available
            return attribute;
        }
        return encryptionService.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        if (encryptionService == null) {
            // Fallback for cases where Spring context is not available
            return dbData;
        }
        return encryptionService.decrypt(dbData);
    }
}

