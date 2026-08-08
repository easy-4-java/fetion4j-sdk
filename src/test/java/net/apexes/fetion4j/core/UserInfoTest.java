package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.user.*;
import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInfoTest {

    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        userInfo = new UserInfo();
    }

    @Test
    void shouldInitializeWithDefaults() {
        assertNull(userInfo.getPersonal());
        assertNull(userInfo.getContact());
        assertNotNull(userInfo.getQuota());
    }

    @Test
    void shouldSetAndGetPersonal() {
        Personal p = new Personal(12345, "sip:12345@fetion.com.cn;p=100", "Name");
        userInfo.setPersonal(p);
        assertEquals(p, userInfo.getPersonal());
    }

    @Test
    void shouldSetAndGetContact() {
        Contact c = new Contact("100");
        userInfo.setContact(c);
        assertEquals(c, userInfo.getContact());
    }

    @Test
    void shouldSetAndGetQuota() {
        Quota q = new Quota();
        userInfo.setQuota(q);
        assertEquals(q, userInfo.getQuota());
    }

    @Test
    void shouldReturnZeroVersionWhenPersonalIsNull() {
        assertEquals("0", userInfo.getPersonalVersion());
    }

    @Test
    void shouldReturnPersonalVersion() {
        Personal p = new Personal(12345, "sip:12345@fetion.com.cn;p=100", "Name");
        p.setVersion("42");
        userInfo.setPersonal(p);
        assertEquals("42", userInfo.getPersonalVersion());
    }

    @Test
    void shouldReturnZeroVersionWhenContactIsNull() {
        assertEquals("0", userInfo.getContactVersion());
    }

    @Test
    void shouldReturnContactVersion() {
        Contact c = new Contact("99");
        userInfo.setContact(c);
        assertEquals("99", userInfo.getContactVersion());
    }

    @Test
    void shouldReturnMeaningfulToString() {
        Personal p = new Personal(12345, "sip:12345@fetion.com.cn;p=100", "Name");
        p.setVersion("1");
        userInfo.setPersonal(p);
        Contact c = new Contact("100");
        userInfo.setContact(c);
        String str = userInfo.toString();
        assertTrue(str.contains("UserInfo"));
        assertTrue(str.contains("1"));
    }
}
