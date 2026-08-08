package net.apexes.fetion4j.core.client.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PasswordEncrypterV4Test {

    @Test
    void shouldEncryptPasswordWithUserId() {
        String result = PasswordEncrypterV4.encryptV4(123456789, "password");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // SHA1 produces 20 bytes = 40 hex chars
        assertEquals(40, result.length());
    }

    @Test
    void shouldEncryptPlainPassword() {
        String result = PasswordEncrypterV4.encryptV4("password");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(40, result.length());
    }

    @Test
    void shouldProduceConsistentResults() {
        String r1 = PasswordEncrypterV4.encryptV4("test");
        String r2 = PasswordEncrypterV4.encryptV4("test");
        assertEquals(r1, r2);
    }

    @Test
    void shouldProduceDifferentResultsForDifferentPasswords() {
        String r1 = PasswordEncrypterV4.encryptV4("password1");
        String r2 = PasswordEncrypterV4.encryptV4("password2");
        assertNotEquals(r1, r2);
    }

    @Test
    void shouldProduceDifferentResultsForDifferentUserIds() {
        String r1 = PasswordEncrypterV4.encryptV4(111, "password");
        String r2 = PasswordEncrypterV4.encryptV4(222, "password");
        assertNotEquals(r1, r2);
    }

    @Test
    void shouldEncryptV4Temp() {
        String digest = PasswordEncrypterV4.encryptV4("test");
        String result = PasswordEncrypterV4.encryptV4Temp(123456789, digest);
        assertNotNull(result);
        assertEquals(40, result.length());
    }
}
