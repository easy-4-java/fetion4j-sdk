package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.sipc.RequestMessage;
import net.apexes.fetion4j.core.sipc.Sipc;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import org.junit.jupiter.api.Test;

class ClientHelperTest {

    @Test
    void shouldCreateSipcMessageKey() {
        RequestMessage msg = new RequestMessage("R");
        msg.setCallId(1);
        msg.setSequence(2);
        String key = ClientHelper.createSipcMessageKey(msg);
        assertEquals("1_2_R", key);
    }

    @Test
    void shouldDetectGroupUri() {
        assertTrue(ClientHelper.isGroupUri("sip:PG1234@fetion.com.cn"));
        assertFalse(ClientHelper.isGroupUri("sip:123456789@fetion.com.cn;p=100"));
        assertFalse(ClientHelper.isGroupUri(null));
    }

    @Test
    void shouldDetectMobileUri() {
        assertTrue(ClientHelper.isMobileUri("tel:13800138000"));
        assertFalse(ClientHelper.isMobileUri("sip:123456789@fetion.com.cn;p=100"));
        assertFalse(ClientHelper.isMobileUri(null));
    }

    @Test
    void shouldDetectSipUri() {
        assertTrue(ClientHelper.isSipUri("sip:123456789@fetion.com.cn;p=1234"));
        assertFalse(ClientHelper.isSipUri("tel:13800138000"));
        assertFalse(ClientHelper.isSipUri(null));
        assertFalse(ClientHelper.isSipUri(""));
    }

    @Test
    void shouldGetSidFromUri() {
        assertEquals("123456789", ClientHelper.getSidFromUri("sip:123456789@fetion.com.cn;p=1234"));
        assertNull(ClientHelper.getSidFromUri("tel:13800138000"));
        assertNull(ClientHelper.getSidFromUri(null));
    }
}
