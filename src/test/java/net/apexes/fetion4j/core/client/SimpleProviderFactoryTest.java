package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import org.junit.jupiter.api.Test;

class SimpleProviderFactoryTest {

    @Test
    void shouldCreateSimpleProvider() {
        FetionContext ctx = createContext();
        SimpleProviderFactory factory = new SimpleProviderFactory(ctx);
        Provider provider = factory.create(13800138000L);
        assertNotNull(provider);
        assertTrue(provider instanceof SimpleProvider);
    }

    @Test
    void shouldCreateDifferentProvidersForDifferentNumbers() {
        FetionContext ctx = createContext();
        SimpleProviderFactory factory = new SimpleProviderFactory(ctx);
        Provider p1 = factory.create(13800138000L);
        Provider p2 = factory.create(13900139000L);
        assertNotNull(p1);
        assertNotNull(p2);
    }

    private FetionContext createContext() {
        return new FetionContext() {
            public String getMachineCode() { return "TEST"; }
            public Account getAccount() {
                return TestHelper.createAccount(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
            }
            public AuthSupportable getAuthSupportable() { return null; }
            public SystemConfig getSystemConfig() { return null; }
            public UserInfo getUserInfo() { return new UserInfo(); }
            public CmccMobileValidator getCmccMobileValidator() { return null; }
            public LogHandler getLogHandler() { return TestHelper.createNoopLogHandler(); }
        };
    }
}
