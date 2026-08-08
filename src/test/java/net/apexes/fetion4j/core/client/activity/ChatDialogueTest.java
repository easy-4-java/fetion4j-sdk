package net.apexes.fetion4j.core.client.activity;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.client.*;
import net.apexes.fetion4j.core.sipc.*;
import net.apexes.fetion4j.core.user.*;
import org.junit.jupiter.api.Test;

class ChatDialogueTest {

    @Test
    void shouldCreateChatDialogue() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        ChatDialogue dialogue = new ChatDialogue(ctx, controller, 1, buddy);
        assertEquals(buddy, dialogue.getBuddy());
        assertEquals(1, dialogue.getCallId());
    }

    @Test
    void shouldGetBuddy() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        Buddy buddy = new Buddy(12345, "tel:13800138000", "Mobile", Relation.BUDDY);
        ChatDialogue dialogue = new ChatDialogue(ctx, controller, 2, buddy);
        assertEquals(buddy, dialogue.getBuddy());
    }

    @Test
    void shouldReceiveMessageWithoutError() {
        FetionContext ctx = createContext();
        Controller controller = new Controller(ctx);
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        ChatDialogue dialogue = new ChatDialogue(ctx, controller, 1, buddy);
        RequestMessage msg = new RequestMessage("M");
        dialogue.receive(msg);
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
