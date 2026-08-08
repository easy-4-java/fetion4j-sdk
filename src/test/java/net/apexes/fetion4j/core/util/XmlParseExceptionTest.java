package net.apexes.fetion4j.core.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class XmlParseExceptionTest {

    @Test
    void shouldCreateWithNameAndMessage() {
        XmlParseException ex = new XmlParseException("root", "unexpected token");
        assertTrue(ex.getMessage().contains("root"));
        assertTrue(ex.getMessage().contains("unexpected token"));
        assertEquals(XmlParseException.NO_LINE, ex.getLineNr());
    }

    @Test
    void shouldCreateWithNullName() {
        XmlParseException ex = new XmlParseException(null, "error");
        assertTrue(ex.getMessage().contains("the XML definition"));
    }

    @Test
    void shouldCreateWithLineNr() {
        XmlParseException ex = new XmlParseException("root", 42, "bad element");
        assertTrue(ex.getMessage().contains("root"));
        assertTrue(ex.getMessage().contains("42"));
        assertTrue(ex.getMessage().contains("bad element"));
        assertEquals(42, ex.getLineNr());
    }

    @Test
    void shouldExtendRuntimeException() {
        XmlParseException ex = new XmlParseException("root", "error");
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void shouldHaveNoLineConstant() {
        assertEquals(-1, XmlParseException.NO_LINE);
    }
}
