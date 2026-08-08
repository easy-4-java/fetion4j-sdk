package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.client.*;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import net.apexes.fetion4j.core.user.*;
import org.junit.jupiter.api.Test;

class FetionConsoleImplTest {

    @Test
    void shouldReportNotClosedInitially() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        FetionConsoleImpl console = new FetionConsoleImpl(controller);
        assertFalse(console.isClosed());
    }

    @Test
    void shouldCloseAndReportClosed() throws FetionException {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        FetionConsoleImpl console = new FetionConsoleImpl(controller);
        console.close();
        assertTrue(console.isClosed());
    }

    @Test
    void shouldGetUserInfo() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        FetionConsoleImpl console = new FetionConsoleImpl(controller);
        UserInfo info = console.getUserInfo();
        assertNotNull(info);
    }

    private FetionContext createContext() {
        return new FetionContext() {
            public String getMachineCode() { return "TEST"; }
            public Account getAccount() {
                return TestHelper.createAccount(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
            }
            public AuthSupportable getAuthSupportable() { return null; }
            public SystemConfig getSystemConfig() { return null; }
            public UserInfo getUserInfo() {
                UserInfo info = new UserInfo();
                Personal p = new Personal(123456789, "sip:123456789@fetion.com.cn;p=1234", "Self");
                p.setVersion("1");
                info.setPersonal(p);
                info.setContact(new Contact("100"));
                return info;
            }
            public CmccMobileValidator getCmccMobileValidator() { return null; }
            public LogHandler getLogHandler() { return TestHelper.createNoopLogHandler(); }
        };
    }
}
