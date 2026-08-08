package net.apexes.fetion4j.core.client.activity;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.client.*;
import net.apexes.fetion4j.core.sipc.*;
import net.apexes.fetion4j.core.user.*;
import org.junit.jupiter.api.Test;

class ActivityClassesTest {

    @Test
    void shouldCreateAddBuddyActivity() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        AddBuddyActivity activity = new AddBuddyActivity(ctx, controller, 1);
        assertEquals(1, activity.getCallId());
    }

    @Test
    void shouldCreateDeleteBuddyActivity() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        DeleteBuddyActivity activity = new DeleteBuddyActivity(ctx, controller, 2);
        assertEquals(2, activity.getCallId());
    }

    @Test
    void shouldCreateSubActivity() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        SubActivity activity = new SubActivity(ctx, controller, 3);
        assertEquals(3, activity.getCallId());
    }

    @Test
    void shouldCreateMainDialogue() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        MainDialogue dialogue = new MainDialogue(ctx, controller, 1);
        assertEquals(1, dialogue.getCallId());
    }

    @Test
    void mainDialogueShouldReceiveBNMessage() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        MainDialogue dialogue = new MainDialogue(ctx, controller, 1);
        // BN message with unknown N value should be handled gracefully
        RequestMessage msg = new RequestMessage("BN");
        msg.setField(Sipc.FIELD_N, "UnknownEvent");
        msg.setBody("<events/>");
        dialogue.receive(msg);
    }

    @Test
    void mainDialogueShouldReceiveNonBNMessage() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        MainDialogue dialogue = new MainDialogue(ctx, controller, 1);
        RequestMessage msg = new RequestMessage("R");
        dialogue.receive(msg);
    }

    @Test
    void mainDialogueCloseShouldTerminateTimer() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        MainDialogue dialogue = new MainDialogue(ctx, controller, 1);
        dialogue.close();
    }

    @Test
    void templateShouldHaveConstants() {
        assertNotNull(Template.TMPL_SYSTEM_CONFIG);
        assertNotNull(Template.TMPL_SYSTEM_CONFIG_INIT);
        assertNotNull(Template.TMPT_USER_AUTH);
        assertNotNull(Template.TMPL_ADD_BUDDY);
        assertNotNull(Template.TMPL_DELETE_BUDDY);
        assertTrue(Template.TMPL_SYSTEM_CONFIG.contains("{mobile-no}"));
        assertTrue(Template.TMPT_USER_AUTH.contains("{machine-code}"));
        assertTrue(Template.TMPL_ADD_BUDDY.contains("{uri}"));
        assertTrue(Template.TMPL_DELETE_BUDDY.contains("{user-id}"));
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
