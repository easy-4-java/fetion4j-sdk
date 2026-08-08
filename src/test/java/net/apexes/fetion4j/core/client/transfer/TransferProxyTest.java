package net.apexes.fetion4j.core.client.transfer;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.client.*;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import net.apexes.fetion4j.core.util.XmlElement;
import net.apexes.fetion4j.core.util.XmlElementHelper;
import org.junit.jupiter.api.Test;

class TransferProxyTest {

    @Test
    void shouldCreateTransferProxy() {
        FetionContext ctx = createContext();
        TransferProxy proxy = new TransferProxy(ctx);
        assertNotNull(proxy);
    }

    @Test
    void shouldCreateTransferProxyWithNullConfig() {
        FetionContext ctx = createContext();
        TransferProxy proxy = new TransferProxy(ctx);
        assertNotNull(proxy);
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

    private FetionContext createContextWithConfig(SystemConfig config) {
        return new FetionContext() {
            public String getMachineCode() { return "TEST"; }
            public Account getAccount() {
                return TestHelper.createAccount(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
            }
            public AuthSupportable getAuthSupportable() { return null; }
            public SystemConfig getSystemConfig() { return config; }
            public UserInfo getUserInfo() { return new UserInfo(); }
            public CmccMobileValidator getCmccMobileValidator() { return null; }
            public LogHandler getLogHandler() { return TestHelper.createNoopLogHandler(); }
        };
    }
}
