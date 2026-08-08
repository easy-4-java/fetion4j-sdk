package net.apexes.fetion4j.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class XmlElementHelperTest {

    @TempDir
    File tempDir;

    @Test
    void shouldGetNodeByPath() {
        XmlElement root = new XmlElement();
        root.setName("config");
        XmlElement servers = new XmlElement();
        servers.setName("servers");
        XmlElement proxy = new XmlElement();
        proxy.setName("sipc-proxy");
        proxy.setContent("221.176.31.43:8080");
        servers.addChild(proxy);
        root.addChild(servers);
        XmlElement result = XmlElementHelper.getNode(root, "/config/servers/sipc-proxy");
        assertNotNull(result);
        assertEquals("221.176.31.43:8080", result.getContent());
    }

    @Test
    void shouldReturnNullForInvalidPath() {
        XmlElement root = new XmlElement();
        root.setName("config");
        assertNull(XmlElementHelper.getNode(root, "/config/nonexistent"));
    }

    @Test
    void shouldReturnNullForWrongRootPath() {
        XmlElement root = new XmlElement();
        root.setName("config");
        assertNull(XmlElementHelper.getNode(root, "/other/path"));
    }

    @Test
    void shouldGetNodeContent() {
        XmlElement root = new XmlElement();
        root.setName("config");
        XmlElement child = new XmlElement();
        child.setName("value");
        child.setContent("test");
        root.addChild(child);
        assertEquals("test", XmlElementHelper.getNodeContent(root, "/config/value"));
    }

    @Test
    void shouldReturnNullContentForMissingPath() {
        XmlElement root = new XmlElement();
        root.setName("config");
        assertNull(XmlElementHelper.getNodeContent(root, "/config/missing"));
    }

    @Test
    void shouldGetNodeAttribute() {
        XmlElement root = new XmlElement();
        root.setName("config");
        XmlElement child = new XmlElement();
        child.setName("servers");
        child.setAttribute("version", "1");
        root.addChild(child);
        assertEquals("1", XmlElementHelper.getNodeAttribute(root, "/config/servers", "version"));
    }

    @Test
    void shouldGetNodeAttributeWithDefault() {
        XmlElement root = new XmlElement();
        root.setName("config");
        assertNull(XmlElementHelper.getNodeAttribute(root, "/config/servers", "version", "default"));
    }

    @Test
    void shouldOpenXmlFile() throws IOException {
        File xmlFile = new File(tempDir, "test.xml");
        try (FileWriter fw = new FileWriter(xmlFile)) {
            fw.write("<root><child>text</child></root>");
        }
        XmlElement xml = XmlElementHelper.open(xmlFile, "UTF-8");
        assertNotNull(xml);
        assertEquals("root", xml.getName());
    }

    @Test
    void shouldWriteXmlFile() throws IOException {
        XmlElement xml = new XmlElement();
        xml.setName("root");
        XmlElement child = new XmlElement();
        child.setName("child");
        child.setContent("text");
        xml.addChild(child);
        File xmlFile = new File(tempDir, "output.xml");
        XmlElementHelper.write(xml, xmlFile, "UTF-8");
        assertTrue(xmlFile.exists());
        XmlElement readBack = XmlElementHelper.open(xmlFile, "UTF-8");
        assertEquals("root", readBack.getName());
    }

    @Test
    void shouldThrowForNonExistentFile() {
        File nonExistent = new File(tempDir, "nonexistent.xml");
        assertThrows(IOException.class, () -> XmlElementHelper.open(nonExistent, "UTF-8"));
    }
}
