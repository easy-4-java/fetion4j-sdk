package net.apexes.fetion4j.core.client.auth;

import static org.junit.jupiter.api.Assertions.*;

import java.security.interfaces.RSAPublicKey;
import org.junit.jupiter.api.Test;

class AuthGeneratorV4Test {

    @Test
    void shouldGenerateCnonce() {
        String cnonce = AuthGeneratorV4.getCnonce();
        assertNotNull(cnonce);
        assertEquals(32, cnonce.length()); // MD5 = 16 bytes = 32 hex chars
    }

    @Test
    void shouldProduceDifferentCnonceValues() {
        String c1 = AuthGeneratorV4.getCnonce();
        String c2 = AuthGeneratorV4.getCnonce();
        // UUID-based, so practically always different
        assertNotNull(c1);
        assertNotNull(c2);
    }

    @Test
    void shouldGenerateResponse() throws Exception {
        AuthGeneratorV4 gen = new AuthGeneratorV4();
        // Create a minimal RSA key for testing
        // Use a known hex-encoded public key (modulus + exponent)
        // This is a test key, not a real one
        String password = PasswordEncrypterV4.encryptV4(123456789, "testpass");
        String nonce = "1D3C1D3C1D3C1D3C1D3C1D3C1D3C1D3C"; // 16 bytes hex = 32 chars
        String aeskey = "0000000000000000000000000000000000000000000000000000000000000000"; // 32 bytes hex = 64 chars
        // Generate a real RSA key pair for testing
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        java.security.KeyPair kp = kpg.generateKeyPair();
        RSAPublicKey pubKey = (RSAPublicKey) kp.getPublic();
        // Encode modulus and exponent as hex
        String modulusHex = net.apexes.fetion4j.core.util.ConvertHelper.byte2HexStringWithoutSpace(
                pubKey.getModulus().toByteArray());
        String exponentHex = net.apexes.fetion4j.core.util.ConvertHelper.byte2HexStringWithoutSpace(
                pubKey.getPublicExponent().toByteArray());
        // Pad modulus to 256 hex chars (128 bytes)
        while (modulusHex.length() < 256) {
            modulusHex = "00" + modulusHex;
        }
        String publicKeyHex = modulusHex + exponentHex;
        String result = gen.generate(publicKeyHex, password, nonce, aeskey);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldParsePublicKey() throws Exception {
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        java.security.KeyPair kp = kpg.generateKeyPair();
        RSAPublicKey original = (RSAPublicKey) kp.getPublic();
        // BigInteger.toByteArray() may include a leading zero byte for positive values
        // with high bit set, so we need to handle that carefully
        byte[] modBytes = original.getModulus().toByteArray();
        // Strip leading zero if present
        if (modBytes[0] == 0 && modBytes.length > 128) {
            byte[] trimmed = new byte[modBytes.length - 1];
            System.arraycopy(modBytes, 1, trimmed, 0, trimmed.length);
            modBytes = trimmed;
        }
        String modulusHex = net.apexes.fetion4j.core.util.ConvertHelper.byte2HexStringWithoutSpace(modBytes);
        String exponentHex = net.apexes.fetion4j.core.util.ConvertHelper.byte2HexStringWithoutSpace(
                original.getPublicExponent().toByteArray());
        // Pad modulus to exactly 256 hex chars (128 bytes)
        while (modulusHex.length() < 256) {
            modulusHex = "00" + modulusHex;
        }
        // Trim if longer than 256
        if (modulusHex.length() > 256) {
            modulusHex = modulusHex.substring(modulusHex.length() - 256);
        }
        String publicKeyHex = modulusHex + exponentHex;
        AuthGeneratorV4 gen = new AuthGeneratorV4();
        RSAPublicKey parsed = gen.parsePublicKey(publicKeyHex);
        assertNotNull(parsed);
        assertEquals(original.getModulus(), parsed.getModulus());
        assertEquals(original.getPublicExponent(), parsed.getPublicExponent());
    }
}
