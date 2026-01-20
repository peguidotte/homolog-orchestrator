package com.aegis.homolog.orchestrator.services;

import com.aegis.homolog.orchestrator.config.EncryptionProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EncryptionService")
class EncryptionServiceTest {

    private static final String TEST_SECRET_KEY = "test-only-key-do-not-use-prod!!";

    private EncryptionService encryptionService;

    @BeforeEach
    void setUp() {
        EncryptionProperties properties = new EncryptionProperties();
        properties.setSecretKey(TEST_SECRET_KEY);
        encryptionService = new EncryptionService(properties);
    }

    @Nested
    @DisplayName("encrypt")
    class EncryptTests {

        @Test
        @DisplayName("should encrypt plaintext successfully")
        void shouldEncryptPlaintextSuccessfully() {
            // Arrange
            String plaintext = "my-secret-token";

            // Act
            String encrypted = encryptionService.encrypt(plaintext);

            // Assert
            assertThat(encrypted).isNotNull();
            assertThat(encrypted).isNotEqualTo(plaintext);
            assertThat(encrypted).isNotBlank();
        }

        @Test
        @DisplayName("should return null for null input")
        void shouldReturnNullForNullInput() {
            // Act
            String result = encryptionService.encrypt(null);

            // Assert
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("should return empty for empty input")
        void shouldReturnEmptyForEmptyInput() {
            // Act
            String result = encryptionService.encrypt("");

            // Assert
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("should produce different ciphertext for same plaintext (unique IV)")
        void shouldProduceDifferentCiphertextForSamePlaintext() {
            // Arrange
            String plaintext = "my-secret-token";

            // Act
            String encrypted1 = encryptionService.encrypt(plaintext);
            String encrypted2 = encryptionService.encrypt(plaintext);

            // Assert - Each encryption should produce different output due to random IV
            assertThat(encrypted1).isNotEqualTo(encrypted2);
        }
    }

    @Nested
    @DisplayName("decrypt")
    class DecryptTests {

        @Test
        @DisplayName("should decrypt ciphertext successfully")
        void shouldDecryptCiphertextSuccessfully() {
            // Arrange
            String plaintext = "my-secret-token";
            String encrypted = encryptionService.encrypt(plaintext);

            // Act
            String decrypted = encryptionService.decrypt(encrypted);

            // Assert
            assertThat(decrypted).isEqualTo(plaintext);
        }

        @Test
        @DisplayName("should return null for null input")
        void shouldReturnNullForNullInput() {
            // Act
            String result = encryptionService.decrypt(null);

            // Assert
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("should return empty for empty input")
        void shouldReturnEmptyForEmptyInput() {
            // Act
            String result = encryptionService.decrypt("");

            // Assert
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("should throw exception for invalid ciphertext")
        void shouldThrowExceptionForInvalidCiphertext() {
            // Arrange
            String invalidCiphertext = "not-valid-base64-ciphertext!!!";

            // Act & Assert
            assertThatThrownBy(() -> encryptionService.decrypt(invalidCiphertext))
                    .isInstanceOf(EncryptionService.EncryptionException.class)
                    .hasMessageContaining("Failed to decrypt");
        }

        @Test
        @DisplayName("should throw exception for tampered ciphertext")
        void shouldThrowExceptionForTamperedCiphertext() {
            // Arrange
            String plaintext = "my-secret-token";
            String encrypted = encryptionService.encrypt(plaintext);
            // Tamper with the ciphertext
            String tampered = encrypted.substring(0, encrypted.length() - 5) + "XXXXX";

            // Act & Assert
            assertThatThrownBy(() -> encryptionService.decrypt(tampered))
                    .isInstanceOf(EncryptionService.EncryptionException.class);
        }
    }

    @Nested
    @DisplayName("roundtrip")
    class RoundtripTests {

        @Test
        @DisplayName("should handle special characters")
        void shouldHandleSpecialCharacters() {
            // Arrange
            String plaintext = "p@ss:w0rd!#$%^&*()_+-=[]{}|;':\",./<>?";

            // Act
            String encrypted = encryptionService.encrypt(plaintext);
            String decrypted = encryptionService.decrypt(encrypted);

            // Assert
            assertThat(decrypted).isEqualTo(plaintext);
        }

        @Test
        @DisplayName("should handle unicode characters")
        void shouldHandleUnicodeCharacters() {
            // Arrange
            String plaintext = "密码 пароль كلمة السر 🔐";

            // Act
            String encrypted = encryptionService.encrypt(plaintext);
            String decrypted = encryptionService.decrypt(encrypted);

            // Assert
            assertThat(decrypted).isEqualTo(plaintext);
        }

        @Test
        @DisplayName("should handle long text")
        void shouldHandleLongText() {
            // Arrange
            String plaintext = "A".repeat(10000);

            // Act
            String encrypted = encryptionService.encrypt(plaintext);
            String decrypted = encryptionService.decrypt(encrypted);

            // Assert
            assertThat(decrypted).isEqualTo(plaintext);
        }

        @Test
        @DisplayName("should handle JWT-like token")
        void shouldHandleJwtLikeToken() {
            // Arrange
            String plaintext = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

            // Act
            String encrypted = encryptionService.encrypt(plaintext);
            String decrypted = encryptionService.decrypt(encrypted);

            // Assert
            assertThat(decrypted).isEqualTo(plaintext);
        }
    }

    /**
     * Key validation tests use manual instantiation because they test
     * error scenarios during EncryptionService initialization.
     * These are unit tests that don't need Spring context.
     */
    @Nested
    @DisplayName("key validation")
    class KeyValidationTests {

        @Test
        @DisplayName("should throw exception when key is null")
        void shouldThrowExceptionWhenKeyIsNull() {
            // Arrange
            EncryptionProperties props = new EncryptionProperties();
            props.setSecretKey(null);

            // Act & Assert
            assertThatThrownBy(() -> new EncryptionService(props))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("not configured");
        }

        @Test
        @DisplayName("should throw exception when key is blank")
        void shouldThrowExceptionWhenKeyIsBlank() {
            // Arrange
            EncryptionProperties props = new EncryptionProperties();
            props.setSecretKey("   ");

            // Act & Assert
            assertThatThrownBy(() -> new EncryptionService(props))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("not configured");
        }

        @Test
        @DisplayName("should throw exception when key is too short")
        void shouldThrowExceptionWhenKeyIsTooShort() {
            // Arrange
            EncryptionProperties props = new EncryptionProperties();
            props.setSecretKey("short"); // Less than 16 chars

            // Act & Assert
            assertThatThrownBy(() -> new EncryptionService(props))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("too short");
        }

        @Test
        @DisplayName("should work with minimum key length (16 chars)")
        void shouldWorkWithMinimumKeyLength() {
            // Arrange
            EncryptionProperties props = new EncryptionProperties();
            props.setSecretKey("exactly16chars!!");  // Exactly 16 chars
            EncryptionService service = new EncryptionService(props);
            String plaintext = "test";

            // Act
            String encrypted = service.encrypt(plaintext);
            String decrypted = service.decrypt(encrypted);

            // Assert
            assertThat(decrypted).isEqualTo(plaintext);
        }

        @Test
        @DisplayName("should work with long key (truncated to 32 bytes)")
        void shouldWorkWithLongKey() {
            // Arrange
            EncryptionProperties props = new EncryptionProperties();
            props.setSecretKey("A".repeat(100)); // Very long key, will be truncated
            EncryptionService service = new EncryptionService(props);
            String plaintext = "test";

            // Act
            String encrypted = service.encrypt(plaintext);
            String decrypted = service.decrypt(encrypted);

            // Assert
            assertThat(decrypted).isEqualTo(plaintext);
        }
    }
}

