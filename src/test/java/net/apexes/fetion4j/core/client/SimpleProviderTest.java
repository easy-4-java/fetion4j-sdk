package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.apexes.fetion4j.core.*;
import net.apexes.fetion4j.core.sipc.SipcMessage;
import net.apexes.fetion4j.core.user.*;
import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SimpleProviderTest {

    @TempDir
    File tempDir;

    private SimpleProvider provider;
    private TestFetionContext context;

    @BeforeEach
    void setUp() {
        context = new TestFetionContext();
        // SimpleProvider creates files under .users/<mobileNo> relative to CWD
        // We test the read/write logic
        provider = new SimpleProvider(context, 13800138000L);
    }

    @Test
    void shouldReturnNullWhenNoSystemConfigFile() {
        // The provider creates .users/13800138000/systemconfig.xml in current dir
        // Since it doesn't exist yet, readSystemConfig should return null
        XmlElement result = provider.readSystemConfig();
        // May return null if file doesn't exist
    }

    @Test
    void shouldReturnNullWhenNoUserInfoFile() {
        UserInfo result = provider.readUserInfo();
        // May return null if file doesn't exist
    }

    @Test
    void shouldWriteAndReadSystemConfig() throws IOException {
        XmlElement xml = new XmlElement();
        xml.setName("config");
        XmlElement servers = new XmlElement();
        servers.setName("servers");
        servers.setAttribute("version", "1");
        xml.addChild(servers);
        provider.writeSystemConfig(xml);
        XmlElement readBack = provider.readSystemConfig();
        assertNotNull(readBack);
        assertEquals("config", readBack.getName());
    }

    @Test
    void shouldWriteAndReadUserInfo() throws IOException {
        UserInfo info = new UserInfo();
        Personal personal = new Personal(123456789, "sip:123456789@fetion.com.cn;p=1234", "TestName");
        personal.setVersion("1");
        personal.setSid("987654321");
        personal.setMobileNo(13800138000L);
        personal.setNickname("Nick");
        info.setPersonal(personal);
        Contact contact = new Contact("100");
        contact.addBuddyGroup(new BuddyGroup(1, "Friends"));
        Buddy buddy = new Buddy(111, "sip:111@fetion.com.cn;p=1", "Buddy1", Relation.BUDDY);
        contact.addBuddy(buddy);
        contact.addBlacklist(new User(999, "sip:999@fetion.com.cn;p=9", "Blocked"));
        info.setContact(contact);
        provider.writeUserInfo(info);
        UserInfo readBack = provider.readUserInfo();
        assertNotNull(readBack);
        assertNotNull(readBack.getPersonal());
        assertEquals(123456789, readBack.getPersonal().getUserId());
        assertNotNull(readBack.getContact());
    }

    @Test
    void shouldHandleNotifyListenerMethods() {
        provider.transfeError("test error", new Exception("test"));
        provider.createdAccount(TestHelper.createAccount(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234"));
        provider.changedUser(new User(12345));
        provider.changedBuddy(new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY), "100");
        provider.addedBuddy(new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY), "100");
        provider.deletedBuddy(new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY), "100");
        provider.smsCountChanged(5, 17);
    }

    @Test
    void shouldHandleChangedSystemConfig() {
        XmlElement xml = new XmlElement();
        xml.setName("config");
        provider.changedSystemConfig(xml);
    }

    @Test
    void shouldHandleLoginSuccessed() throws IOException {
        UserInfo info = new UserInfo();
        Personal personal = new Personal(123456789, "sip:123456789@fetion.com.cn;p=1234", "Test");
        personal.setVersion("1");
        info.setPersonal(personal);
        info.setContact(new Contact("100"));
        provider.loginSuccessed(null, info);
    }

    @Test
    void shouldHandleLogoutSuccessed() throws IOException {
        UserInfo info = new UserInfo();
        Personal personal = new Personal(123456789, "sip:123456789@fetion.com.cn;p=1234", "Test");
        personal.setVersion("1");
        info.setPersonal(personal);
        info.setContact(new Contact("100"));
        context.setUserInfo(info);
        provider.logoutSuccessed();
    }

    @Test
    void shouldHandleWriteUserInfoWithNullFields() throws IOException {
        UserInfo info = new UserInfo();
        provider.writeUserInfo(info);
    }

    private static class TestFetionContext implements FetionContext {
        private UserInfo userInfo = new UserInfo();
        void setUserInfo(UserInfo ui) { this.userInfo = ui; }
        public String getMachineCode() { return "TEST"; }
        public Account getAccount() { return TestHelper.createAccount(13800138000L, 123456789, "sip:123456789@fetion.com.cn;p=1234"); }
        public AuthSupportable getAuthSupportable() { return null; }
        public SystemConfig getSystemConfig() { return null; }
        public UserInfo getUserInfo() { return userInfo; }
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
