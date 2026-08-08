package net.apexes.fetion4j.core.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DigestHelperTest {

    @Test
    void shouldComputeMD5() {
        byte[] result = DigestHelper.MD5("hello".getBytes());
        assertNotNull(result);
        assertEquals(16, result.length);
    }

    @Test
    void shouldComputeSHA1() {
        byte[] result = DigestHelper.SHA1("hello".getBytes());
        assertNotNull(result);
        assertEquals(20, result.length);
    }

    @Test
    void shouldGenerateAESKey() {
        byte[] key = DigestHelper.createAESKey();
        assertNotNull(key);
        assertEquals(32, key.length);
    }

    @Test
    void shouldProduceConsistentMD5() {
        byte[] r1 = DigestHelper.MD5("test".getBytes());
        byte[] r2 = DigestHelper.MD5("test".getBytes());
        assertArrayEquals(r1, r2);
    }

    @Test
    void shouldProduceConsistentSHA1() {
        byte[] r1 = DigestHelper.SHA1("test".getBytes());
        byte[] r2 = DigestHelper.SHA1("test".getBytes());
        assertArrayEquals(r1, r2);
    }
}
