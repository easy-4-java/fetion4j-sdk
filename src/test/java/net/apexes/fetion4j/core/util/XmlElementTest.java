package net.apexes.fetion4j.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class XmlElementTest {

    @Test
    void shouldCreateDefaultElement() {
        XmlElement el = new XmlElement();
        assertNull(el.getName());
        assertEquals("", el.getContent());
        assertEquals(0, el.getLineNr());
        assertEquals(0, el.getChildrenCount());
    }

    @Test
    void shouldSetAndGetName() {
        XmlElement el = new XmlElement();
        el.setName("root");
        assertEquals("root", el.getName());
    }

    @Test
    void shouldSetAndGetContent() {
        XmlElement el = new XmlElement();
        el.setContent("text");
        assertEquals("text", el.getContent());
    }

    @Test
    void shouldAddAndRemoveChildren() {
        XmlElement parent = new XmlElement();
        parent.setName("root");
        XmlElement child = new XmlElement();
        child.setName("child");
        parent.addChild(child);
        assertEquals(1, parent.getChildrenCount());
        parent.removeChild(child);
        assertEquals(0, parent.getChildrenCount());
    }

    @Test
    void shouldThrowWhenAddingSelf() {
        XmlElement el = new XmlElement();
        el.setName("root");
        assertThrows(IllegalArgumentException.class, () -> el.addChild(el));
    }

    @Test
    void shouldGetChildByName() {
        XmlElement parent = new XmlElement();
        parent.setName("root");
        XmlElement child = new XmlElement();
        child.setName("child");
        parent.addChild(child);
        assertEquals(child, parent.getChild("child"));
        assertNull(parent.getChild("nonexistent"));
    }

    @Test
    void shouldGetChildByIndex() {
        XmlElement parent = new XmlElement();
        parent.setName("root");
        XmlElement child = new XmlElement();
        child.setName("child");
        parent.addChild(child);
        assertEquals(child, parent.getChild(0));
    }

    @Test
    void shouldGetChildrenByName() {
        XmlElement parent = new XmlElement();
        parent.setName("root");
        XmlElement c1 = new XmlElement();
        c1.setName("item");
        XmlElement c2 = new XmlElement();
        c2.setName("item");
        XmlElement c3 = new XmlElement();
        c3.setName("other");
        parent.addChild(c1);
        parent.addChild(c2);
        parent.addChild(c3);
        List<XmlElement> items = parent.getChildren("item");
        assertEquals(2, items.size());
    }

    @Test
    void shouldGetAllChildren() {
        XmlElement parent = new XmlElement();
        parent.setName("root");
        XmlElement c1 = new XmlElement();
        c1.setName("a");
        XmlElement c2 = new XmlElement();
        c2.setName("b");
        parent.addChild(c1);
        parent.addChild(c2);
        assertEquals(2, parent.getChildren().size());
    }

    @Test
    void shouldSetAndGetParent() {
        XmlElement parent = new XmlElement();
        parent.setName("root");
        XmlElement child = new XmlElement();
        child.setName("child");
        parent.addChild(child);
        assertEquals(parent, child.getParent());
    }

    @Test
    void shouldSetAndGetAttributes() {
        XmlElement el = new XmlElement();
        el.setAttribute("key", "value");
        assertEquals("value", el.getStringAttribute("key"));
        assertNull(el.getStringAttribute("missing"));
        assertEquals("default", el.getStringAttribute("missing", "default"));
    }

    @Test
    void shouldSetAndGetIntAttributes() {
        XmlElement el = new XmlElement();
        el.setIntAttribute("count", 42);
        assertEquals(42, el.getIntAttribute("count"));
        assertEquals(0, el.getIntAttribute("missing"));
        assertEquals(99, el.getIntAttribute("missing", 99));
    }

    @Test
    void shouldSetAndGetLongAttributes() {
        XmlElement el = new XmlElement();
        el.setLongAttribute("big", 123456789L);
        assertEquals(123456789L, el.getLongAttribute("big"));
        assertEquals(0L, el.getLongAttribute("missing"));
        assertEquals(99L, el.getLongAttribute("missing", 99L));
    }

    @Test
    void shouldSetAndGetDoubleAttributes() {
        XmlElement el = new XmlElement();
        el.setDoubleAttribute("pi", 3.14);
        assertEquals(3.14, el.getDoubleAttribute("pi"), 0.001);
        assertEquals(0.0, el.getDoubleAttribute("missing"), 0.001);
        assertEquals(1.0, el.getDoubleAttribute("missing", 1.0), 0.001);
    }

    @Test
    void shouldGetBooleanAttribute() {
        XmlElement el = new XmlElement();
        el.setAttribute("flag", "yes");
        assertTrue(el.getBooleanAttribute("flag", "yes", "no", false));
        assertFalse(el.getBooleanAttribute("flag", "yes", "no", true) == false);
    }

    @Test
    void shouldReturnDefaultForMissingBoolean() {
        XmlElement el = new XmlElement();
        assertTrue(el.getBooleanAttribute("flag", "yes", "no", true));
        assertFalse(el.getBooleanAttribute("flag", "yes", "no", false));
    }

    @Test
    void shouldRemoveAttribute() {
        XmlElement el = new XmlElement();
        el.setAttribute("key", "value");
        el.removeAttribute("key");
        assertNull(el.getStringAttribute("key"));
    }

    @Test
    void shouldGetAttributeNames() {
        XmlElement el = new XmlElement();
        el.setAttribute("a", "1");
        el.setAttribute("b", "2");
        Set<String> names = el.getAttributeNames();
        assertEquals(2, names.size());
        assertTrue(names.contains("a"));
        assertTrue(names.contains("b"));
    }

    @Test
    void shouldParseFromString() {
        XmlElement el = new XmlElement();
        el.parseString("<root attr=\"val\"><child/></root>");
        assertEquals("root", el.getName());
        assertEquals("val", el.getStringAttribute("attr"));
        assertEquals(1, el.getChildrenCount());
    }

    @Test
    void shouldParseFromStringWithOffset() {
        XmlElement el = new XmlElement();
        el.parseString("xxx<root/>", 3);
        assertEquals("root", el.getName());
    }

    @Test
    void shouldParseFromStringWithOffsetAndEnd() {
        XmlElement el = new XmlElement();
        el.parseString("xxx<root/>yyy", 3, 10);
        assertEquals("root", el.getName());
    }

    @Test
    void shouldWriteToWriter() throws Exception {
        XmlElement el = new XmlElement();
        el.setName("root");
        el.setAttribute("key", "val");
        XmlElement child = new XmlElement();
        child.setName("child");
        child.setContent("text");
        el.addChild(child);
        StringWriter sw = new StringWriter();
        el.write(sw);
        String output = sw.toString();
        assertTrue(output.contains("<root"));
        assertTrue(output.contains("key=\"val\""));
        assertTrue(output.contains("text"));
    }

    @Test
    void shouldWriteWithIndent() throws Exception {
        XmlElement el = new XmlElement();
        el.setName("root");
        StringWriter sw = new StringWriter();
        el.write(sw, true, 4);
        assertTrue(sw.toString().contains("<root"));
    }

    @Test
    void shouldWriteWithIndentOnly() throws Exception {
        XmlElement el = new XmlElement();
        el.setName("root");
        StringWriter sw = new StringWriter();
        el.write(sw, 4);
        assertTrue(sw.toString().contains("<root"));
    }

    @Test
    void shouldParseFromReader() throws Exception {
        XmlElement el = new XmlElement();
        el.parseFromReader(new StringReader("<root/>"));
        assertEquals("root", el.getName());
    }

    @Test
    void shouldParseCharArray() {
        XmlElement el = new XmlElement();
        el.parseCharArray("<root/>".toCharArray(), 0, 7);
        assertEquals("root", el.getName());
    }

    @Test
    void shouldImplementSerializable() {
        XmlElement el = new XmlElement();
        assertTrue(el instanceof java.io.Serializable);
    }

    @Test
    void shouldReturnIgnoreWhitespace() {
        XmlElement el1 = new XmlElement();
        assertTrue(el1.isIgnoreWhitespace());
        XmlElement el2 = new XmlElement(false);
        assertFalse(el2.isIgnoreWhitespace());
    }

    @Test
    void shouldThrowForInvalidIntAttribute() {
        XmlElement el = new XmlElement();
        el.setAttribute("key", "notanumber");
        assertThrows(RuntimeException.class, () -> el.getIntAttribute("key"));
    }

    @Test
    void shouldThrowForInvalidLongAttribute() {
        XmlElement el = new XmlElement();
        el.setAttribute("key", "notanumber");
        assertThrows(RuntimeException.class, () -> el.getLongAttribute("key"));
    }

    @Test
    void shouldThrowForInvalidDoubleAttribute() {
        XmlElement el = new XmlElement();
        el.setAttribute("key", "notanumber");
        assertThrows(RuntimeException.class, () -> el.getDoubleAttribute("key"));
    }
}
