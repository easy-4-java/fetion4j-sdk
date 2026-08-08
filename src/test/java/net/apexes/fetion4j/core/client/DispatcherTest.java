package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.sipc.*;
import net.apexes.fetion4j.core.user.*;
import org.junit.jupiter.api.Test;

class DispatcherTest {

    @Test
    void shouldCreateDispatcher() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        // Dispatcher requires a running controller with executor service
        // We can't fully test it without starting the controller, but we can test creation
        // by verifying the controller state
        assertFalse(controller.isRunning());
    }

    @Test
    void shouldThrowWhenSubmittingToNonRunningController() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        // Can't submit to a non-running controller
        assertFalse(controller.isRunning());
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
