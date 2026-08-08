package net.apexes.fetion4j.core.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringHelperTest {

    @Test
    void shouldQuoteHtmlSpecialChars() {
        assertEquals("&amp;", StringHelper.qouteHtmlSpecialChars("&"));
        assertEquals("&lt;", StringHelper.qouteHtmlSpecialChars("<"));
        assertEquals("&gt;", StringHelper.qouteHtmlSpecialChars(">"));
        assertEquals("&quot;", StringHelper.qouteHtmlSpecialChars("\""));
        assertEquals("&apos;", StringHelper.qouteHtmlSpecialChars("'"));
    }

    @Test
    void shouldReturnNullForNullHtmlQuote() {
        assertNull(StringHelper.qouteHtmlSpecialChars(null));
    }

    @Test
    void shouldUnquoteHtmlSpecialChars() {
        assertEquals("&", StringHelper.unqouteHtmlSpecialChars("&amp;"));
        assertEquals("<", StringHelper.unqouteHtmlSpecialChars("&lt;"));
        assertEquals(">", StringHelper.unqouteHtmlSpecialChars("&gt;"));
        assertEquals("\"", StringHelper.unqouteHtmlSpecialChars("&quot;"));
        assertEquals("'", StringHelper.unqouteHtmlSpecialChars("&apos;"));
        assertEquals(" ", StringHelper.unqouteHtmlSpecialChars("&nbsp;"));
    }

    @Test
    void shouldReturnNullForNullHtmlUnquote() {
        assertNull(StringHelper.unqouteHtmlSpecialChars(null));
    }

    @Test
    void shouldStripHtmlTags() {
        assertEquals("Hello", StringHelper.stripHtmlSpecialChars("<b>Hello</b>"));
        // &nbsp; is replaced with a space character
        assertEquals(" space", StringHelper.stripHtmlSpecialChars("&nbsp;space"));
    }

    @Test
    void shouldReturnNullForNullStrip() {
        assertNull(StringHelper.stripHtmlSpecialChars(null));
    }

    @Test
    void shouldFormatWithPositionalArgs() {
        assertEquals("apple is fruit", StringHelper.format("{0} is {1}", "apple", "fruit"));
    }

    @Test
    void shouldUrlEncode() {
        assertEquals("hello+world", StringHelper.urlEncode("hello world"));
    }

    @Test
    void shouldBase64Decode() {
        // "hello" in base64 is "aGVsbG8="
        byte[] decoded = StringHelper.base64Decode("aGVsbG8=");
        assertNotNull(decoded);
        assertEquals("hello", new String(decoded));
    }

    @Test
    void shouldConvertFirstToLowerCase() {
        assertEquals("hello", StringHelper.firstToLowerCase("Hello"));
    }

    @Test
    void shouldConvertFirstToUpperCase() {
        assertEquals("Hello", StringHelper.firstToUpperCase("hello"));
    }
}
