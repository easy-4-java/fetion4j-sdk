package net.apexes.fetion4j.core.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonalTest {

    @Test
    void shouldCreateEmptyPersonal() {
        Personal p = new Personal();
        assertEquals(0, p.getUserId());
    }

    @Test
    void shouldCreatePersonalWithArgs() {
        Personal p = new Personal(12345, "sip:12345@fetion.com.cn;p=100", "Name");
        assertEquals(12345, p.getUserId());
        assertEquals("sip:12345@fetion.com.cn;p=100", p.getUri());
        assertEquals("Name", p.getName());
    }

    @Test
    void shouldSetAndGetVersion() {
        Personal p = new Personal();
        p.setVersion("12345");
        assertEquals("12345", p.getVersion());
    }

    @Test
    void shouldSetAndGetSid() {
        Personal p = new Personal();
        p.setSid("987654321");
        assertEquals("987654321", p.getSid());
    }

    @Test
    void shouldSetAndGetMobileNo() {
        Personal p = new Personal();
        p.setMobileNo(13800138000L);
        assertEquals(13800138000L, p.getMobileNo());
    }

    @Test
    void shouldSetAndGetNickname() {
        Personal p = new Personal();
        p.setNickname("Nick");
        assertEquals("Nick", p.getNickname());
    }

    @Test
    void shouldSetAndGetImpresa() {
        Personal p = new Personal();
        p.setImpresa("Hello World");
        assertEquals("Hello World", p.getImpresa());
    }

    @Test
    void shouldSetAndGetCarrier() {
        Personal p = new Personal();
        p.setCarrier("CMCC");
        assertEquals("CMCC", p.getCarrier());
    }

    @Test
    void shouldSetAndGetCarrierStatus() {
        Personal p = new Personal();
        p.setCarrierStatus("0");
        assertEquals("0", p.getCarrierStatus());
    }

    @Test
    void shouldSetAndGetSmsOnlineStatus() {
        Personal p = new Personal();
        p.setSmsOnlineStatus("0.0:0:0");
        assertEquals("0.0:0:0", p.getSmsOnlineStatus());
    }

    @Test
    void shouldSetAndGetPresence() {
        Personal p = new Personal();
        p.setPresence(Presence.BUSY);
        assertEquals(Presence.BUSY, p.getPresence());
    }

    @Test
    void shouldSupportSmsWhenCmccAndOnline() {
        Personal p = new Personal();
        p.setCarrier("CMCC");
        p.setCarrierStatus("0");
        p.setSmsOnlineStatus("0.0:0:0");
        assertTrue(p.supportSMS());
    }

    @Test
    void shouldNotSupportSmsWhenNotCmcc() {
        Personal p = new Personal();
        p.setCarrier("UNICOM");
        p.setCarrierStatus("0");
        p.setSmsOnlineStatus("0.0:0:0");
        assertFalse(p.supportSMS());
    }

    @Test
    void shouldNotSupportSmsWhenCarrierStatusNotZero() {
        Personal p = new Personal();
        p.setCarrier("CMCC");
        p.setCarrierStatus("1");
        p.setSmsOnlineStatus("0.0:0:0");
        assertFalse(p.supportSMS());
    }

    @Test
    void shouldReturnNameAsDisplayName() {
        Personal p = new Personal();
        p.setName("MyName");
        assertEquals("MyName", p.getDisplayName());
    }

    @Test
    void shouldReturnNicknameAsDisplayNameWhenNameEmpty() {
        Personal p = new Personal();
        p.setNickname("NickName");
        assertEquals("NickName", p.getDisplayName());
    }

    @Test
    void shouldReturnUserIdAsDisplayNameWhenNameAndNicknameEmpty() {
        Personal p = new Personal(12345, "sip:12345@fetion.com.cn;p=100", null);
        assertEquals("12345", p.getDisplayName());
    }

    @Test
    void shouldReturnMeaningfulToString() {
        Personal p = new Personal(12345, "sip:12345@fetion.com.cn;p=100", "Test");
        p.setVersion("1");
        String str = p.toString();
        assertTrue(str.contains("Personal{"));
        assertTrue(str.contains("12345"));
    }
}
