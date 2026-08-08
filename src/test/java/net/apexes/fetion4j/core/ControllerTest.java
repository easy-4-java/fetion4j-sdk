package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.client.*;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import net.apexes.fetion4j.core.user.Buddy;
import net.apexes.fetion4j.core.user.User;
import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.Test;

class ControllerTest {

    @Test
    void shouldCreateController() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        assertNotNull(controller);
        assertFalse(controller.isRunning());
        assertEquals(ctx, controller.getContext());
    }

    @Test
    void shouldAddAndRemoveNotifyListener() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        NotifyListener listener = createListener();
        controller.addNotifyListener(listener);
        controller.removeNotifyListener(listener);
    }

    @Test
    void shouldFireTransfeErrorWithoutException() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireTransfeError("error", new Exception("test"));
    }

    @Test
    void shouldFireChangedSystemConfig() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireChangedSystemConfig(new XmlElement());
    }

    @Test
    void shouldFireCreatedAccount() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        Account account = new Account(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
        controller.fireCreatedAccount(account);
    }

    @Test
    void shouldFireLoginSuccessed() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireLoginSuccessed(null, new UserInfo());
    }

    @Test
    void shouldFireLogoutSuccessed() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireLogoutSuccessed();
    }

    @Test
    void shouldFireChangedUser() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireChangedUser(new User(12345));
    }

    @Test
    void shouldFireChangedBuddy() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireChangedBuddy(new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", net.apexes.fetion4j.core.user.Relation.BUDDY), "100");
    }

    @Test
    void shouldFireAddedBuddy() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireAddedBuddy(new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", net.apexes.fetion4j.core.user.Relation.BUDDY), "100");
    }

    @Test
    void shouldFireDeletedBuddy() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireDeletedBuddy(new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", net.apexes.fetion4j.core.user.Relation.BUDDY), "100");
    }

    @Test
    void shouldFireSmsCountChanged() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireSmsCountChanged(5, 17);
    }

    private FetionContext createContext() {
        return new FetionContext() {
            public String getMachineCode() { return "TESTMACHINE"; }
            public Account getAccount() {
                return new Account(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
            }
            public AuthSupportable getAuthSupportable() { return null; }
            public SystemConfig getSystemConfig() { return null; }
            public UserInfo getUserInfo() { return new UserInfo(); }
            public CmccMobileValidator getCmccMobileValidator() { return null; }
            public LogHandler getLogHandler() { return new LogHandler() {
                public void transmit(SipcMessage m) {}
                public void receive(SipcMessage m) {}
                public void error(Class<?> c, String msg, Throwable t) {}
                public void debug(Class<?> c, String msg) {}
                public void info(Class<?> c, String msg) {}
                public void warn(Class<?> c, String msg) {}
            }; }
        };
    }

    private NotifyListener createListener() {
        return new NotifyListener() {
            public void transfeError(String message, Exception exception) {}
            public void changedSystemConfig(XmlElement systemConfig) {}
            public void createdAccount(Account account) {}
            public void loginSuccessed(FetionConsole console, UserInfo userInfo) {}
            public void logoutSuccessed() {}
            public void changedUser(User user) {}
            public void changedBuddy(Buddy buddy, String contactVersion) {}
            public void addedBuddy(Buddy buddy, String contactVersion) {}
            public void deletedBuddy(Buddy buddy, String contactVersion) {}
            public void smsCountChanged(int dayCount, int monthCount) {}
        };
    }
}
