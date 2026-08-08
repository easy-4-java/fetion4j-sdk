package net.apexes.fetion4j.core.client.activity;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.client.*;
import net.apexes.fetion4j.core.sipc.RequestMessage;
import net.apexes.fetion4j.core.sipc.Sipc;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import net.apexes.fetion4j.core.user.Buddy;
import net.apexes.fetion4j.core.user.BuddyGroup;
import net.apexes.fetion4j.core.user.Personal;
import net.apexes.fetion4j.core.user.Presence;
import net.apexes.fetion4j.core.user.Relation;
import org.junit.jupiter.api.Test;

class MessageHelperTest {

    @Test
    void shouldCreateLoginRequest() {
        RequestMessage msg = MessageHelper.createLoginRequest(createContext());
        assertNotNull(msg);
        assertEquals(Sipc.METHOD_R, msg.getMethod());
        assertNotNull(msg.getFieldValue(Sipc.FIELD_CN));
        assertNotNull(msg.getFieldValue(Sipc.FIELD_CL));
    }

    @Test
    void shouldCreateLogoutRequest() {
        RequestMessage msg = MessageHelper.createLogoutRequest();
        assertNotNull(msg);
        assertEquals(Sipc.METHOD_R, msg.getMethod());
        assertEquals("0", msg.getFieldValue(Sipc.FIELD_X));
    }

    @Test
    void shouldCreateKeepAliveRequest() {
        RequestMessage msg = MessageHelper.createKeepAliveRequest();
        assertNotNull(msg);
        assertEquals(Sipc.METHOD_R, msg.getMethod());
        assertEquals("KeepAlive", msg.getFieldValue(Sipc.FIELD_N));
        assertTrue(msg.getBody().contains("credentials"));
    }

    @Test
    void shouldCreateFutileRequest() {
        RequestMessage msg = MessageHelper.createFutileRequest();
        assertNotNull(msg);
        assertEquals(Sipc.METHOD_O, msg.getMethod());
        assertEquals("SouthAfrica2010", msg.getFieldValue(Sipc.FIELD_N));
    }

    @Test
    void shouldCreateSubPresenceRequest() {
        RequestMessage msg = MessageHelper.createSubPresenceRequest();
        assertNotNull(msg);
        assertEquals(Sipc.METHOD_SUB, msg.getMethod());
        assertEquals("PresenceV4", msg.getFieldValue(Sipc.FIELD_N));
        assertTrue(msg.getBody().contains("subscription"));
    }

    @Test
    void shouldCreateMsgRequest() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        RequestMessage msg = MessageHelper.createMsgRequest(buddy, "hello", false);
        assertNotNull(msg);
        assertEquals(Sipc.METHOD_M, msg.getMethod());
        assertEquals("sip:12345@fetion.com.cn;p=100", msg.getFieldValue(Sipc.FIELD_T));
        assertEquals("CatMsg", msg.getFieldValue(Sipc.FIELD_N));
        assertEquals("hello", msg.getBody());
    }

    @Test
    void shouldCreateSmsMsgRequest() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        RequestMessage msg = MessageHelper.createMsgRequest(buddy, "hello", true);
        assertEquals("SendCatSMS", msg.getFieldValue(Sipc.FIELD_N));
    }

    @Test
    void shouldCreateAddBuddyRequest() {
        RequestMessage msg = MessageHelper.createAddBuddyRequest(
                "sip:12345@fetion.com.cn;p=100", "LocalName", null, "Description", 0);
        assertNotNull(msg);
        assertEquals(Sipc.METHOD_S, msg.getMethod());
        assertEquals("AddBuddyV4", msg.getFieldValue(Sipc.FIELD_N));
        assertTrue(msg.getBody().contains("sip:12345@fetion.com.cn;p=100"));
        assertTrue(msg.getBody().contains("LocalName"));
    }

    @Test
    void shouldCreateAddBuddyRequestWithGroup() {
        BuddyGroup group = new BuddyGroup(1, "Friends");
        RequestMessage msg = MessageHelper.createAddBuddyRequest(
                "sip:12345@fetion.com.cn;p=100", "Name", group, "Desc", 0);
        assertTrue(msg.getBody().contains("1"));
    }

    @Test
    void shouldCreateAddBuddyRequestWithNullLocalName() {
        RequestMessage msg = MessageHelper.createAddBuddyRequest(
                "sip:12345@fetion.com.cn;p=100", null, null, "Desc", 0);
        assertNotNull(msg);
    }

    @Test
    void shouldCreateDeleteBuddyRequest() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        RequestMessage msg = MessageHelper.createDeleteBuddyRequest(buddy, true);
        assertNotNull(msg);
        assertEquals(Sipc.METHOD_S, msg.getMethod());
        assertEquals("DeleteBuddyV4", msg.getFieldValue(Sipc.FIELD_N));
        assertTrue(msg.getBody().contains("12345"));
        assertTrue(msg.getBody().contains("delete-both=\"1\""));
    }

    @Test
    void shouldCreateDeleteBuddyRequestWithoutDeleteBoth() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        RequestMessage msg = MessageHelper.createDeleteBuddyRequest(buddy, false);
        assertTrue(msg.getBody().contains("delete-both=\"0\""));
    }

    private FetionContext createContext() {
        return new FetionContext() {
            public String getMachineCode() { return "TESTMACHINE"; }
            public Account getAccount() {
                return TestHelper.createAccount(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234");
            }
            public net.apexes.fetion4j.core.AuthSupportable getAuthSupportable() { return null; }
            public SystemConfig getSystemConfig() { return null; }
            public UserInfo getUserInfo() {
                UserInfo info = new UserInfo();
                Personal p = new Personal(123456789, "sip:123456789@fetion.com.cn;p=1234", "Test");
                p.setVersion("1");
                info.setPersonal(p);
                return info;
            }
            public CmccMobileValidator getCmccMobileValidator() { return null; }
            public LogHandler getLogHandler() { return TestHelper.createNoopLogHandler(); }
        };
    }
}
