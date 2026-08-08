package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SystemConfigTest {

    private XmlElement xml;
    private SystemConfig config;

    @BeforeEach
    void setUp() throws Exception {
        String xmlStr = "<config>"
                + "<servers version=\"1\">"
                + "<sipc-proxy>221.176.31.43:8080</sipc-proxy>"
                + "<ssi-app-sign-in-v2>https://nav.fetion.com.cn/login</ssi-app-sign-in-v2>"
                + "<get-pic-code>https://nav.fetion.com.cn/pic</get-pic-code>"
                + "</servers>"
                + "<service-no version=\"2\"/>"
                + "<parameters version=\"3\"/>"
                + "<hints version=\"4\"/>"
                + "<http-applications version=\"5\"/>"
                + "<client-config version=\"6\">"
                + "<item key=\"mobile-no-dist\" value=\"&lt;r&gt;&lt;c v=&quot;cmcc&quot;&gt;&lt;d s=&quot;13500000000&quot; e=&quot;13999999999&quot;/&gt;&lt;/c&gt;&lt;/r&gt;\"/>"
                + "</client-config>"
                + "<services version=\"7\"/>"
                + "</config>";
        xml = new XmlElement();
        xml.parseString(xmlStr);
        config = new SystemConfig(xml);
    }

    @Test
    void shouldGetValueByPath() {
        String value = config.getValue("/config/servers/sipc-proxy");
        assertNotNull(value);
        assertEquals("221.176.31.43:8080", value);
    }

    @Test
    void shouldReturnXml() {
        assertNotNull(config.getXml());
        assertEquals("config", config.getXml().getName());
    }

    @Test
    void shouldGenerateSummary() {
        String summary = config.getSummary();
        assertNotNull(summary);
        assertTrue(summary.contains("1"));
        assertTrue(summary.contains("2"));
    }

    @Test
    void shouldReturnMeaningfulToString() {
        assertNotNull(config.toString());
    }

    @Test
    void shouldUpdateWithNewXml() throws Exception {
        String newXmlStr = "<config>"
                + "<servers version=\"10\">"
                + "<sipc-proxy>10.0.0.1:8080</sipc-proxy>"
                + "</servers>"
                + "</config>";
        XmlElement newXml = new XmlElement();
        newXml.parseString(newXmlStr);
        boolean changed = config.update(newXml);
        assertTrue(changed);
        assertEquals("10.0.0.1:8080", config.getValue("/config/servers/sipc-proxy"));
    }

    @Test
    void shouldReturnFalseWhenNoChanges() throws Exception {
        String newXmlStr = "<config></config>";
        XmlElement newXml = new XmlElement();
        newXml.parseString(newXmlStr);
        boolean changed = config.update(newXml);
        assertFalse(changed);
    }
}
