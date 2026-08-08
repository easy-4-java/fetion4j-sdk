package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.sipc.*;
import net.apexes.fetion4j.core.user.*;
import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.Test;

class ControllerAdvancedTest {

    @Test
    void shouldCreateChatDialogue() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        var dialogue = controller.createChatDialogue(buddy);
        assertNotNull(dialogue);
        assertEquals(buddy, dialogue.getBuddy());
    }

    @Test
    void shouldCreateSubActivity() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        var activity = controller.createSubAcitivity();
        assertNotNull(activity);
    }

    @Test
    void shouldCreateAddBuddyActivity() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        var activity = controller.createAddBuddyActivity();
        assertNotNull(activity);
    }

    @Test
    void shouldCreateDeleteBuddyActivity() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        var activity = controller.createDeleteBuddyActivity();
        assertNotNull(activity);
    }

    @Test
    void shouldGetExecutorServiceWhenNotRunning() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        assertNull(controller.getExecutorService());
    }

    @Test
    void shouldGetTransferWhenNotRunning() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        assertNull(controller.getTransfer());
    }

    @Test
    void shouldReceiveMessageWithUnknownCallId() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        RequestMessage msg = new RequestMessage("R");
        msg.setCallId(999);
        msg.setSequence(1);
        // Should not throw even with unknown callId
        controller.receive(msg);
    }

    @Test
    void shouldFireMultipleEventsInSequence() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        controller.addNotifyListener(createListener());
        controller.fireTransfeError("err1", new Exception("e1"));
        controller.fireTransfeError("err2", new Exception("e2"));
        controller.fireSmsCountChanged(1, 2);
        controller.fireSmsCountChanged(3, 4);
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
