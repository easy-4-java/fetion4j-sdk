package net.apexes.fetion4j.core.client.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthDigestTest {

    @Test
    void shouldParseDigestString() {
        String digest = "Digest algorithm=\"SHA1-sess-v4\",nonce=\"1D3C\",key=\"C3C7\",signature=\"84E8\"";
        AuthDigest auth = AuthDigest.parse(digest);
        assertNotNull(auth);
        assertEquals("SHA1-sess-v4", auth.getAlgorithm());
        assertEquals("1D3C", auth.getNonce());
        assertEquals("C3C7", auth.getKey());
        assertEquals("84E8", auth.getSignature());
        assertNull(auth.getResponse());
    }

    @Test
    void shouldFormatDigestWithoutResponse() {
        String digest = "Digest algorithm=\"SHA1-sess-v4\",nonce=\"1D3C\",key=\"C3C7\",signature=\"84E8\"";
        AuthDigest auth = AuthDigest.parse(digest);
        String str = auth.toString();
        assertTrue(str.startsWith("Digest "));
        assertTrue(str.contains("SHA1-sess-v4"));
        assertTrue(str.contains("nonce=\"1D3C\""));
        assertTrue(str.contains("key=\"C3C7\""));
        assertTrue(str.contains("signature=\"84E8\""));
    }
}
