package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.sipc.RequestMessage;
import net.apexes.fetion4j.core.sipc.ResponseMessage;
import org.junit.jupiter.api.Test;

/**
 * Tests for the DefaultLogHandler inner class of Fetion.
 * We test it by creating a Fetion instance and exercising its log handler.
 */
class FetionDefaultLogHandlerTest {

    @Test
    void shouldCreateFetionWithDefaultLogHandler() {
        // Fetion creates a DefaultLogHandler when no log handler is set
        Fetion fetion = new Fetion(13800138000L);
        assertNotNull(fetion.getLogHandler());
    }

    @Test
    void shouldSetCustomLogHandler() {
        Fetion fetion = new Fetion(13800138000L);
        fetion.setLogHandler(null);
        // After setting null, should still return default handler
        assertNotNull(fetion.getLogHandler());
    }

    @Test
    void shouldGetMachineCode() {
        Fetion fetion = new Fetion(13800138000L);
        assertNotNull(fetion.getMachineCode());
        assertEquals("5DBFE64D4449FBD0AE130C7B12D27A9F", fetion.getMachineCode());
    }

    @Test
    void shouldGetAccountBeforeLogin() {
        Fetion fetion = new Fetion(13800138000L);
        assertNull(fetion.getAccount());
    }

    @Test
    void shouldGetSystemConfigBeforeLogin() {
        Fetion fetion = new Fetion(13800138000L);
        assertNull(fetion.getSystemConfig());
    }

    @Test
    void shouldGetUserInfoBeforeLogin() {
        Fetion fetion = new Fetion(13800138000L);
        assertNull(fetion.getUserInfo());
    }

    @Test
    void shouldGetAuthSupportableBeforeLogin() {
        Fetion fetion = new Fetion(13800138000L);
        assertNull(fetion.getAuthSupportable());
    }

    @Test
    void shouldSetAndGetAuthSupportable() {
        Fetion fetion = new Fetion(13800138000L);
        AuthSupportable support = (captcha, feedback) -> {};
        fetion.setAuthSupportable(support);
        assertEquals(support, fetion.getAuthSupportable());
    }

    @Test
    void shouldGetCmccMobileValidatorBeforeLogin() {
        Fetion fetion = new Fetion(13800138000L);
        assertNull(fetion.getCmccMobileValidator());
    }

    @Test
    void shouldAddAndRemoveNotifyListener() {
        Fetion fetion = new Fetion(13800138000L);
        NotifyListener listener = new NotifyListener() {
            public void transfeError(String message, Exception exception) {}
            public void changedSystemConfig(net.apexes.fetion4j.core.util.XmlElement systemConfig) {}
            public void createdAccount(Account account) {}
            public void loginSuccessed(FetionConsole console, UserInfo userInfo) {}
            public void logoutSuccessed() {}
            public void changedUser(net.apexes.fetion4j.core.user.User user) {}
            public void changedBuddy(net.apexes.fetion4j.core.user.Buddy buddy, String contactVersion) {}
            public void addedBuddy(net.apexes.fetion4j.core.user.Buddy buddy, String contactVersion) {}
            public void deletedBuddy(net.apexes.fetion4j.core.user.Buddy buddy, String contactVersion) {}
            public void smsCountChanged(int dayCount, int monthCount) {}
        };
        fetion.addNotifyListener(listener);
        fetion.removeNotifyListener(listener);
    }

    @Test
    void shouldCreateFetionWithFactory() {
        Fetion fetion = new Fetion(13800138000L, null);
        assertNotNull(fetion);
    }
}
