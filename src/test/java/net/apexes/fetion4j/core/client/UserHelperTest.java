package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.user.*;
import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.Test;

class UserHelperTest {

    @Test
    void shouldConvertUserToXml() {
        User user = new User(12345, "sip:12345@fetion.com.cn;p=100", "TestName");
        XmlElement xml = UserHelper.toXml(user);
        assertNotNull(xml);
        assertEquals("user", xml.getName());
        assertEquals(12345, xml.getIntAttribute("userId"));
        assertEquals("sip:12345@fetion.com.cn;p=100", xml.getStringAttribute("uri"));
        assertEquals("TestName", xml.getStringAttribute("name"));
    }

    @Test
    void shouldConvertPersonalToXml() {
        Personal p = new Personal(12345, "sip:12345@fetion.com.cn;p=100", "Name");
        p.setVersion("100");
        p.setSid("987654321");
        p.setMobileNo(13800138000L);
        p.setNickname("Nick");
        p.setImpresa("Status");
        p.setCarrier("CMCC");
        p.setCarrierStatus("0");
        p.setSmsOnlineStatus("0.0:0:0");
        p.setPresence(Presence.ONLINE);
        XmlElement xml = UserHelper.toXml(p);
        assertNotNull(xml);
        assertEquals("personal", xml.getName());
    }

    @Test
    void shouldConvertBuddyToXml() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Name", Relation.BUDDY);
        XmlElement xml = UserHelper.toXml(buddy);
        assertNotNull(xml);
        assertEquals("buddy", xml.getName());
    }

    @Test
    void shouldConvertXmlToUser() {
        XmlElement xml = new XmlElement();
        xml.setName("user");
        xml.setAttribute("userId", "12345");
        xml.setAttribute("uri", "sip:12345@fetion.com.cn;p=100");
        xml.setAttribute("name", "TestName");
        Object result = UserHelper.toUser(xml);
        assertNotNull(result);
        assertTrue(result instanceof User);
        User user = (User) result;
        assertEquals(12345, user.getUserId());
        assertEquals("sip:12345@fetion.com.cn;p=100", user.getUri());
        assertEquals("TestName", user.getName());
    }

    @Test
    void shouldConvertXmlToPersonal() {
        XmlElement xml = new XmlElement();
        xml.setName("personal");
        xml.setAttribute("userId", "12345");
        xml.setAttribute("uri", "sip:12345@fetion.com.cn;p=100");
        xml.setAttribute("name", "Name");
        xml.setAttribute("version", "100");
        xml.setAttribute("sid", "987654321");
        xml.setAttribute("mobileNo", "13800138000");
        xml.setAttribute("nickname", "Nick");
        xml.setAttribute("impresa", "Status");
        xml.setAttribute("carrier", "CMCC");
        xml.setAttribute("carrierStatus", "0");
        xml.setAttribute("smsOnlineStatus", "0.0:0:0");
        Object result = UserHelper.toUser(xml);
        assertNotNull(result);
        assertTrue(result instanceof Personal);
        Personal p = (Personal) result;
        assertEquals(12345, p.getUserId());
        assertEquals("100", p.getVersion());
        assertEquals("987654321", p.getSid());
        assertEquals(13800138000L, p.getMobileNo());
        assertEquals("Nick", p.getNickname());
    }

    @Test
    void shouldConvertXmlToBuddy() {
        XmlElement xml = new XmlElement();
        xml.setName("buddy");
        xml.setAttribute("userId", "12345");
        xml.setAttribute("uri", "sip:12345@fetion.com.cn;p=100");
        xml.setAttribute("name", "Name");
        xml.setAttribute("relation", "1");
        Object result = UserHelper.toUser(xml);
        assertNotNull(result);
        assertTrue(result instanceof Buddy);
    }

    @Test
    void shouldReturnNullForInvalidClassName() {
        XmlElement xml = new XmlElement();
        xml.setName("nonexistent");
        Object result = UserHelper.toUser(xml);
        assertNull(result);
    }
}
