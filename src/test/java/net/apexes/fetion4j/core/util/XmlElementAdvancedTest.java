package net.apexes.fetion4j.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Additional tests for XmlElement to cover more parsing and writing paths.
 */
class XmlElementAdvancedTest {

    @Test
    void shouldParseElementWithAttributes() {
        XmlElement el = new XmlElement();
        el.parseString("<root key=\"value\" num=\"42\"><child/></root>");
        assertEquals("root", el.getName());
        assertEquals("value", el.getStringAttribute("key"));
        assertEquals(42, el.getIntAttribute("num"));
        assertEquals(1, el.getChildrenCount());
    }

    @Test
    void shouldParseNestedElements() {
        XmlElement el = new XmlElement();
        el.parseString("<root><a><b><c>text</c></b></a></root>");
        assertEquals("root", el.getName());
        XmlElement a = el.getChild("a");
        assertNotNull(a);
        XmlElement b = a.getChild("b");
        assertNotNull(b);
        XmlElement c = b.getChild("c");
        assertNotNull(c);
        assertEquals("text", c.getContent());
    }

    @Test
    void shouldParseSelfClosingElement() {
        XmlElement el = new XmlElement();
        el.parseString("<root/>");
        assertEquals("root", el.getName());
        assertEquals(0, el.getChildrenCount());
    }

    @Test
    void shouldParseElementWithContent() {
        XmlElement el = new XmlElement();
        el.parseString("<root>Hello World</root>");
        assertEquals("root", el.getName());
        assertEquals("Hello World", el.getContent());
    }

    @Test
    void shouldParseElementWithMultipleChildren() {
        XmlElement el = new XmlElement();
        el.parseString("<root><a/><b/><c/></root>");
        assertEquals(3, el.getChildrenCount());
    }

    @Test
    void shouldWriteEmptyElement() throws Exception {
        XmlElement el = new XmlElement();
        el.setName("root");
        StringWriter sw = new StringWriter();
        el.write(sw);
        assertTrue(sw.toString().contains("<root/>") || sw.toString().contains("<root />"));
    }

    @Test
    void shouldWriteElementWithContent() throws Exception {
        XmlElement el = new XmlElement();
        el.setName("root");
        el.setContent("text");
        StringWriter sw = new StringWriter();
        el.write(sw);
        String output = sw.toString();
        assertTrue(output.contains("<root>text</root>"));
    }

    @Test
    void shouldWriteElementWithChildren() throws Exception {
        XmlElement root = new XmlElement();
        root.setName("root");
        XmlElement child = new XmlElement();
        child.setName("child");
        child.setContent("data");
        root.addChild(child);
        StringWriter sw = new StringWriter();
        root.write(sw);
        String output = sw.toString();
        assertTrue(output.contains("<root>"));
        assertTrue(output.contains("<child>data</child>"));
        assertTrue(output.contains("</root>"));
    }

    @Test
    void shouldEncodeSpecialCharsWhenWriting() throws Exception {
        XmlElement el = new XmlElement();
        el.setName("root");
        el.setContent("<>&\"'");
        StringWriter sw = new StringWriter();
        el.write(sw);
        String output = sw.toString();
        assertTrue(output.contains("&lt;"));
        assertTrue(output.contains("&gt;"));
        assertTrue(output.contains("&amp;"));
    }

    @Test
    void shouldWriteElementWithAttributes() throws Exception {
        XmlElement el = new XmlElement();
        el.setName("root");
        el.setAttribute("key", "value");
        StringWriter sw = new StringWriter();
        el.write(sw);
        String output = sw.toString();
        assertTrue(output.contains("key=\"value\""));
    }

    @Test
    void shouldHandleIgnoreWhitespaceConstructor() {
        XmlElement el = new XmlElement(false);
        assertFalse(el.isIgnoreWhitespace());
    }

    @Test
    void shouldHandleEntityConstructor() {
        Map<String, char[]> entities = new HashMap<>();
        entities.put("custom", new char[]{'X'});
        XmlElement el = new XmlElement(entities);
        assertNotNull(el);
    }

    @Test
    void shouldHandleEntityAndWhitespaceConstructor() {
        Map<String, char[]> entities = new HashMap<>();
        XmlElement el = new XmlElement(entities, false);
        assertNotNull(el);
    }

    @Test
    void shouldParseWithSpecialCharacters() {
        XmlElement el = new XmlElement();
        el.parseString("<root>&amp; &lt; &gt; &quot; &apos;</root>");
        assertEquals("& < > \" '", el.getContent());
    }

    @Test
    void shouldThrowForMalformedXml() {
        XmlElement el = new XmlElement();
        assertThrows(XmlParseException.class, () -> el.parseString("not xml"));
    }

    @Test
    void shouldParseElementWithComment() {
        XmlElement el = new XmlElement();
        el.parseString("<!-- comment --><root/>");
        assertEquals("root", el.getName());
    }

    @Test
    void shouldParseElementWithProcessingInstruction() {
        XmlElement el = new XmlElement();
        el.parseString("<?xml version=\"1.0\"?><root/>");
        assertEquals("root", el.getName());
    }

    @Test
    void shouldGetAttributeWithDefaultValue() {
        XmlElement el = new XmlElement();
        assertEquals("default", el.getAttribute("missing", "default"));
    }

    @Test
    void shouldParseFromReaderWithLineNr() throws Exception {
        XmlElement el = new XmlElement();
        el.parseFromReader(new StringReader("<root/>"), 10);
        assertEquals("root", el.getName());
    }

    @Test
    void shouldParseStringWithOffset2() {
        XmlElement el = new XmlElement();
        el.parseString("prefix<root/>", 6, 13, 1);
        assertEquals("root", el.getName());
    }

    @Test
    void shouldParseStringWithOffsetAndLineNr() {
        XmlElement el = new XmlElement();
        el.parseString("prefix<root/>", 6, 13, 5);
        assertEquals("root", el.getName());
    }

    @Test
    void shouldWriteStandaloneWithIndent() throws Exception {
        XmlElement root = new XmlElement();
        root.setName("root");
        XmlElement child = new XmlElement();
        child.setName("child");
        root.addChild(child);
        StringWriter sw = new StringWriter();
        root.write(sw, true, 2);
        String output = sw.toString();
        assertTrue(output.contains("<root>"));
        assertTrue(output.contains("<child"));
    }

    @Test
    void shouldWriteNullNameElement() throws Exception {
        XmlElement el = new XmlElement();
        el.setContent("text");
        StringWriter sw = new StringWriter();
        el.write(sw);
        assertEquals("text", sw.toString());
    }

    @Test
    void shouldParseMultipleSiblingElements() {
        XmlElement el = new XmlElement();
        el.parseString("<root><item id=\"1\"/><item id=\"2\"/><item id=\"3\"/></root>");
        assertEquals(3, el.getChildren("item").size());
    }

    @Test
    void shouldParseElementWithLongAttribute() {
        XmlElement el = new XmlElement();
        el.setLongAttribute("big", 9999999999L);
        assertEquals(9999999999L, el.getLongAttribute("big"));
    }

    @Test
    void shouldParseElementWithDoubleAttribute() {
        XmlElement el = new XmlElement();
        el.setDoubleAttribute("pi", 3.14159);
        assertEquals(3.14159, el.getDoubleAttribute("pi"), 0.00001);
    }

    @Test
    void shouldHandleEmptyChildrenList() {
        XmlElement el = new XmlElement();
        el.setName("root");
        assertTrue(el.getChildren().isEmpty());
        assertTrue(el.getChildren("child").isEmpty());
    }

    @Test
    void shouldParseRealisticSipcXml() {
        XmlElement el = new XmlElement();
        el.parseString("<results status-code=\"200\"><contacts version=\"407799076\">"
                + "<buddies><buddy user-id=\"123456789\" uri=\"sip:123456789@fetion.com.cn;p=1335\" "
                + "local-name=\"Test\" relation-status=\"1\"/></buddies></contacts></results>");
        assertEquals("results", el.getName());
        assertEquals(200, el.getIntAttribute("status-code"));
        XmlElement contacts = el.getChild("contacts");
        assertNotNull(contacts);
        assertEquals("407799076", contacts.getStringAttribute("version"));
    }
}
