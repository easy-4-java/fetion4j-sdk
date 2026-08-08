package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.sipc.*;
import net.apexes.fetion4j.core.user.*;
import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.Test;

class ActivityTest {

    @Test
    void shouldCreateActivityAndGetCallId() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        TestActivity activity = new TestActivity(ctx, controller, 42);
        assertEquals(42, activity.getCallId());
        assertEquals(ctx, activity.getContext());
        assertEquals(controller, activity.getController());
    }

    @Test
    void shouldGetContextAndController() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        TestActivity activity = new TestActivity(ctx, controller, 1);
        assertNotNull(activity.getContext());
        assertNotNull(activity.getController());
    }

    private static class TestActivity extends Activity {
        TestActivity(FetionContext context, Controller controller, int callId) {
            super(context, controller, callId);
        }
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
