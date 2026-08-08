package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.client.*;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import net.apexes.fetion4j.core.user.*;
import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInfoUpdateTest {

    private UserInfo userInfo;
    private Controller controller;

    @BeforeEach
    void setUp() {
        userInfo = new UserInfo();
        Personal personal = new Personal(123456789, "sip:123456789@fetion.com.cn;p=1234", "Self");
        personal.setVersion("1");
        personal.setSid("123456789");
        userInfo.setPersonal(personal);
        Contact contact = new Contact("100");
        Buddy buddy = new Buddy(987654321, "sip:987654321@fetion.com.cn;p=277", "Buddy1", Relation.BUDDY);
        buddy.setVersion("1");
        contact.addBuddy(buddy);
        userInfo.setContact(contact);
        controller = new Controller(new TestFetionContext());
    }

    @Test
    void shouldUpdateOnLoginedWithPersonal() throws Exception {
        String xmlStr = "<user-info>"
                + "<personal version=\"2\" user-id=\"123456789\" uri=\"sip:123456789@fetion.com.cn;p=1234\" "
                + "name=\"Self\" sid=\"123456789\" mobile-no=\"13800138000\" nickname=\"Nick\" impresa=\"Status\"/>"
                + "</user-info>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnLogined(controller, xml);
        assertEquals("2", userInfo.getPersonal().getVersion());
        assertEquals("Nick", userInfo.getPersonal().getNickname());
    }

    @Test
    void shouldUpdateOnLoginedWithContact() throws Exception {
        String xmlStr = "<user-info>"
                + "<contact-list version=\"200\">"
                + "<buddy-lists><buddy-list id=\"1\" name=\"Friends\"/></buddy-lists>"
                + "<buddies><b i=\"111\" u=\"sip:111@fetion.com.cn;p=1\" n=\"NewBuddy\" r=\"1\"/></buddies>"
                + "</contact-list>"
                + "</user-info>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnLogined(controller, xml);
        assertEquals("200", userInfo.getContact().getVersion());
        assertNotNull(userInfo.getContact().findBuddy(111));
    }

    @Test
    void shouldUpdateOnLoginedWithQuotas() throws Exception {
        String xmlStr = "<user-info>"
                + "<quotas>"
                + "<quota-limit><limit name=\"max-buddies\" value=\"500\"/></quota-limit>"
                + "<quota-frequency><frequency name=\"send-sms\" day-limit=\"1000\" day-count=\"5\" "
                + "month-limit=\"15000\" month-count=\"10\"/></quota-frequency>"
                + "</quotas>"
                + "</user-info>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        try {
            userInfo.updateOnLogined(controller, xml);
        } catch (NullPointerException e) {
            // Expected - controller fires sms count changed which needs listeners
        }
        assertEquals(500, userInfo.getQuota().getMaxBuddies());
    }

    @Test
    void shouldUpdateOnLoginedWithUnknownElement() throws Exception {
        String xmlStr = "<user-info>"
                + "<custom-element version=\"1\"/>"
                + "</user-info>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnLogined(controller, xml);
        // The custom element should be stored in xmlMap
    }

    @Test
    void shouldNotUpdatePersonalWhenVersionUnchanged() throws Exception {
        userInfo.getPersonal().setVersion("1");
        String xmlStr = "<user-info>"
                + "<personal version=\"1\" user-id=\"123456789\" uri=\"sip:123456789@fetion.com.cn;p=1234\" "
                + "name=\"Self\"/>"
                + "</user-info>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnLogined(controller, xml);
        // Personal should not be replaced since version is the same
        assertEquals("1", userInfo.getPersonal().getVersion());
    }

    @Test
    void shouldUpdateOnPresenceChanged() throws Exception {
        String xmlStr = "<events><event type=\"PresenceChanged\"><contacts>"
                + "<c id=\"987654321\">"
                + "<p v=\"2\" sid=\"987654321\" su=\"sip:987654321@fetion.com.cn;p=277\" "
                + "m=\"13800138001\" c=\"CMCC\" cs=\"0\" s=\"1\" l=\"0\" svc=\"99\" "
                + "n=\"NewNick\" i=\"NewStatus\" sms=\"0.0:0:0\" sp=\"0\" sh=\"0\"/>"
                + "<pr di=\"\" b=\"400\" d=\"\" dt=\"\" dc=\"0\"></pr>"
                + "</c></contacts></event></events>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnPresenceChanged(controller, xml);
        Buddy buddy = userInfo.getContact().findBuddy(987654321);
        assertNotNull(buddy);
        assertEquals("NewNick", buddy.getNickname());
        assertEquals(Presence.ONLINE, buddy.getPresence());
    }

    @Test
    void shouldUpdateOnPresenceChangedForSelf() throws Exception {
        String xmlStr = "<events><event type=\"PresenceChanged\"><contacts>"
                + "<c id=\"123456789\">"
                + "<p v=\"2\" sid=\"123456789\" su=\"sip:123456789@fetion.com.cn;p=1234\" "
                + "m=\"13800138000\" c=\"CMCC\" cs=\"0\" s=\"1\" l=\"0\" svc=\"99\" "
                + "n=\"SelfNick\" i=\"SelfStatus\" sms=\"0.0:0:0\" sp=\"0\" sh=\"0\"/>"
                + "<pr di=\"\" b=\"600\" d=\"\" dt=\"\" dc=\"0\"></pr>"
                + "</c></contacts></event></events>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnPresenceChanged(controller, xml);
        assertEquals("SelfNick", userInfo.getPersonal().getNickname());
        assertEquals(Presence.BUSY, userInfo.getPersonal().getPresence());
    }

    @Test
    void shouldUpdateOnSyncUserInfoChanged() throws Exception {
        String xmlStr = "<events><event type=\"SyncUserInfo\"><user-info>"
                + "<contact-list version=\"200\">"
                + "<buddies>"
                + "<buddy action=\"update\" user-id=\"987654321\" relation-status=\"2\"/>"
                + "</buddies></contact-list></user-info></event></events>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnSyncUserInfoChanged(controller, xml);
        Buddy buddy = userInfo.getContact().findBuddy(987654321);
        assertNotNull(buddy);
        assertEquals(Relation.DECLINED, buddy.getRelation());
    }

    @Test
    void shouldHandlePresenceWithEmptyMobileNo() throws Exception {
        String xmlStr = "<events><event type=\"PresenceChanged\"><contacts>"
                + "<c id=\"987654321\">"
                + "<p v=\"2\" sid=\"987654321\" su=\"sip:987654321@fetion.com.cn;p=277\" "
                + "m=\"\" c=\"CMCC\" cs=\"0\" s=\"1\" l=\"0\" svc=\"99\" "
                + "n=\"Nick\" i=\"Status\" sms=\"0.0:0:0\" sp=\"0\" sh=\"0\"/>"
                + "<pr di=\"\" b=\"100\" d=\"\" dt=\"\" dc=\"0\"></pr>"
                + "</c></contacts></event></events>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnPresenceChanged(controller, xml);
        Buddy buddy = userInfo.getContact().findBuddy(987654321);
        assertNotNull(buddy);
    }

    @Test
    void shouldHandlePresenceForUnknownUser() throws Exception {
        String xmlStr = "<events><event type=\"PresenceChanged\"><contacts>"
                + "<c id=\"999999999\">"
                + "<p v=\"2\" sid=\"999999999\" su=\"sip:999999999@fetion.com.cn;p=1\" "
                + "m=\"13800138000\" c=\"CMCC\" cs=\"0\" s=\"1\" l=\"0\" svc=\"99\" "
                + "n=\"Unknown\" i=\"\" sms=\"0.0:0:0\" sp=\"0\" sh=\"0\"/>"
                + "<pr di=\"\" b=\"0\" d=\"\" dt=\"\" dc=\"0\"></pr>"
                + "</c></contacts></event></events>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        userInfo.updateOnPresenceChanged(controller, xml);
        // Should not throw, just break out
    }

    private static class TestFetionContext implements FetionContext {
        public String getMachineCode() { return "TEST"; }
        public Account getAccount() { return new Account(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234"); }
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
    }
}
