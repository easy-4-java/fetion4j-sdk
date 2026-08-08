package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.user.Presence;
import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void shouldCreateAccountWithConstructorArgs() {
        Account account = new Account(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
        assertEquals(123456789, account.getUserId());
        assertEquals(13800138000L, account.getMobileNo());
        assertEquals("sip:123456789@fetion.com.cn;p=1234", account.getUri());
        assertEquals("123456789", account.getSid());
        assertNull(account.getPassword());
        assertEquals(Presence.ONLINE, account.getPresence());
        assertNotNull(account.getAesKey());
        assertEquals(32, account.getAesKey().length);
        assertNotNull(account.getAesIV());
        assertEquals(16, account.getAesIV().length);
    }

    @Test
    void shouldSetAndGetPassword() {
        Account account = new Account(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
        account.setPassword("secret");
        assertEquals("secret", account.getPassword());
    }

    @Test
    void shouldSetAndGetPresence() {
        Account account = new Account(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
        account.setPresence(Presence.BUSY);
        assertEquals(Presence.BUSY, account.getPresence());
    }

    @Test
    void shouldReturnMeaningfulToString() {
        Account account = new Account(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
        String str = account.toString();
        assertTrue(str.contains("Account{"));
        assertTrue(str.contains("123456789"));
        assertTrue(str.contains("13800138000"));
    }
}
