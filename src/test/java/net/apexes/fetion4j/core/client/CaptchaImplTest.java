package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CaptchaImplTest {

    @Test
    void shouldCreateWithAllArgs() {
        byte[] imageData = new byte[]{1, 2, 3};
        CaptchaImpl captcha = new CaptchaImpl("picc-PasswordErrorMax", "GeneralPic",
                "reason", "tips", "abc123", imageData);
        assertEquals("picc-PasswordErrorMax", captcha.getVerifyAlgorithm());
        assertEquals("GeneralPic", captcha.getVerifyType());
        assertEquals("reason", captcha.getText());
        assertEquals("tips", captcha.getTips());
        assertEquals("abc123", captcha.getImageId());
        assertArrayEquals(imageData, captcha.getImageData());
        assertNull(captcha.getCode());
        assertEquals(0, captcha.getFailCount());
    }

    @Test
    void shouldSetAndGetCode() {
        CaptchaImpl captcha = new CaptchaImpl("alg", "type", "text", "tips", "id", new byte[0]);
        captcha.setCode("ABCD");
        assertEquals("ABCD", captcha.getCode());
    }

    @Test
    void shouldSetAndGetFailCount() {
        CaptchaImpl captcha = new CaptchaImpl("alg", "type", "text", "tips", "id", new byte[0]);
        captcha.setFailCount(3);
        assertEquals(3, captcha.getFailCount());
    }

    @Test
    void shouldReturnMeaningfulToString() {
        CaptchaImpl captcha = new CaptchaImpl("alg", "type", "text", "tips", "id", new byte[0]);
        captcha.setCode("X");
        captcha.setFailCount(2);
        String str = captcha.toString();
        assertTrue(str.contains("Captcha{"));
        assertTrue(str.contains("alg"));
        assertTrue(str.contains("X"));
        assertTrue(str.contains("2"));
    }
}
