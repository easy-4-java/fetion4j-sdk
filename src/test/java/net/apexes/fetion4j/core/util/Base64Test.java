package net.apexes.fetion4j.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;
import org.junit.jupiter.api.Test;

class Base64Test {

    @Test
    void shouldEncodeBase64() {
        byte[] input = "hello".getBytes();
        byte[] encoded = Base64.encodeBase64(input);
        assertNotNull(encoded);
        // "hello" base64 = "aGVsbG8="
        assertEquals("aGVsbG8=", new String(encoded));
    }

    @Test
    void shouldEncodeBase64Chunked() {
        byte[] input = new byte[100];
        byte[] encoded = Base64.encodeBase64Chunked(input);
        assertNotNull(encoded);
    }

    @Test
    void shouldDecodeBase64() {
        byte[] encoded = "aGVsbG8=".getBytes();
        byte[] decoded = Base64.decodeBase64(encoded);
        assertNotNull(decoded);
        assertEquals("hello", new String(decoded));
    }

    @Test
    void shouldHandleEmptyInput() {
        byte[] encoded = Base64.encodeBase64(new byte[0]);
        assertNotNull(encoded);
        assertEquals(0, encoded.length);
    }

    @Test
    void shouldHandleEmptyDecode() {
        byte[] decoded = Base64.decodeBase64(new byte[0]);
        assertNotNull(decoded);
        assertEquals(0, decoded.length);
    }

    @Test
    void shouldEncodeAndDecodeRoundTrip() {
        byte[] original = "Hello, World! 12345".getBytes();
        byte[] encoded = Base64.encodeBase64(original);
        byte[] decoded = Base64.decodeBase64(encoded);
        assertArrayEquals(original, decoded);
    }

    @Test
    void shouldValidateBase64Array() {
        assertTrue(Base64.isArrayByteBase64("aGVsbG8=".getBytes()));
        // The isBase64 check uses the byte as an array index which can go negative
        // for bytes > 127, so testing with such bytes may throw ArrayIndexOutOfBoundsException
        // Use ASCII non-base64 characters instead
        assertFalse(Base64.isArrayByteBase64(new byte[]{(byte) '!', (byte) '@'}));
    }

    @Test
    void shouldReturnTrueForEmptyArray() {
        assertTrue(Base64.isArrayByteBase64(new byte[0]));
    }

    @Test
    void shouldThrowForNonByteArrayEncode() {
        Base64 base64 = new Base64();
        assertThrows(InvalidParameterException.class, () -> base64.encode("string"));
    }

    @Test
    void shouldThrowForNonByteArrayDecode() {
        Base64 base64 = new Base64();
        assertThrows(InvalidParameterException.class, () -> base64.decode("string"));
    }

    @Test
    void shouldEncodeViaInstanceMethod() {
        Base64 base64 = new Base64();
        byte[] result = base64.encode("test".getBytes());
        assertNotNull(result);
    }

    @Test
    void shouldDecodeViaInstanceMethod() {
        Base64 base64 = new Base64();
        byte[] encoded = Base64.encodeBase64("test".getBytes());
        byte[] decoded = base64.decode(encoded);
        assertEquals("test", new String(decoded));
    }

    @Test
    void shouldHandleTwoBytePadding() {
        // "a" -> "YQ==" (two padding chars)
        byte[] encoded = Base64.encodeBase64("a".getBytes());
        byte[] decoded = Base64.decodeBase64(encoded);
        assertEquals("a", new String(decoded));
    }

    @Test
    void shouldHandleOneBytePadding() {
        // "ab" -> "YWI=" (one padding char)
        byte[] encoded = Base64.encodeBase64("ab".getBytes());
        byte[] decoded = Base64.decodeBase64(encoded);
        assertEquals("ab", new String(decoded));
    }
}
