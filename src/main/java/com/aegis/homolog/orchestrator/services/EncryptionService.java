package com.aegis.homolog.orchestrator.services;

import com.aegis.homolog.orchestrator.config.EncryptionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {

    private static final Logger log = LoggerFactory.getLogger(EncryptionService.class);

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12; // 96 bits
    private static final int GCM_TAG_LENGTH = 128; // bits

    private final SecretKeySpec secretKey;
    private final SecureRandom secureRandom;

    public EncryptionService(EncryptionProperties properties) {
        validateSecretKey(properties.getSecretKey());
        byte[] keyBytes = normalizeKey(properties.getSecretKey());
        this.secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        this.secureRandom = new SecureRandom();
        log.info("EncryptionService initialized with AES-256-GCM");
    }

    /**
     * Validates that the secret key is properly configured.
     *
     * @throws IllegalStateException if key is missing or too short
     */
    private void validateSecretKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalStateException(
                    "Encryption secret key is not configured. " +
                    "Set 'aegis.security.encryption.secret-key' property or ENCRYPTION_SECRET_KEY environment variable."
            );
        }
        if (key.length() < 16) {
            throw new IllegalStateException(
                    "Encryption secret key is too short. Minimum 16 characters required, 32 recommended for AES-256."
            );
        }
    }

    /**
     * Encrypts a plaintext string.
     *
     * @param plaintext the text to encrypt
     * @return Base64-encoded encrypted string (IV + ciphertext)
     */
    public String encrypt(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            return plaintext;
        }

        try {
            // Generate random IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            // Combine IV + ciphertext
            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + ciphertext.length);
            byteBuffer.put(iv);
            byteBuffer.put(ciphertext);

            return Base64.getEncoder().encodeToString(byteBuffer.array());
        } catch (Exception e) {
            log.error("Encryption failed", e);
            throw new EncryptionException("Failed to encrypt data", e);
        }
    }

    /**
     * Decrypts an encrypted string.
     *
     * @param encryptedText Base64-encoded encrypted string (IV + ciphertext)
     * @return the decrypted plaintext
     */
    public String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return encryptedText;
        }

        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedText);

            // Extract IV and ciphertext
            ByteBuffer byteBuffer = ByteBuffer.wrap(decoded);
            byte[] iv = new byte[GCM_IV_LENGTH];
            byteBuffer.get(iv);
            byte[] ciphertext = new byte[byteBuffer.remaining()];
            byteBuffer.get(ciphertext);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            byte[] plaintext = cipher.doFinal(ciphertext);
            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Decryption failed", e);
            throw new EncryptionException("Failed to decrypt data", e);
        }
    }

    /**
     * Normalizes the key to 32 bytes (AES-256).
     * If key is shorter, it's padded. If longer, it's truncated.
     */
    private byte[] normalizeKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] normalizedKey = new byte[32]; // AES-256

        System.arraycopy(
                keyBytes, 0,
                normalizedKey, 0,
                Math.min(keyBytes.length, normalizedKey.length)
        );

        return normalizedKey;
    }

    /**
     * Exception thrown when encryption/decryption fails.
     */
    public static class EncryptionException extends RuntimeException {
        public EncryptionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}


