package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.sipc.*;
import org.junit.jupiter.api.Test;

class DialogueTest {

    @Test
    void shouldCreateDialogueAndGetCallId() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        TestDialogue dialogue = new TestDialogue(ctx, controller, 5);
        assertEquals(5, dialogue.getCallId());
    }

    @Test
    void shouldReceiveMessage() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        TestDialogue dialogue = new TestDialogue(ctx, controller, 1);
        RequestMessage msg = new RequestMessage("R");
        dialogue.receive(msg);
        assertTrue(dialogue.received);
    }

    private static class TestDialogue extends Dialogue {
        boolean received = false;
        TestDialogue(FetionContext context, Controller controller, int callId) {
            super(context, controller, callId);
        }
        public void receive(SipcMessage message) {
            received = true;
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
