package net.apexes.fetion4j.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class ConvertHelperTest {

    @Test
    void shouldConvertBytesToHexString() {
        byte[] b = new byte[]{(byte) 0xCA, (byte) 0xFE};
        assertEquals("CA FE", ConvertHelper.byte2HexString(b));
    }

    @Test
    void shouldReturnNullForNullByteArray() {
        assertEquals("null", ConvertHelper.byte2HexString(null));
    }

    @Test
    void shouldConvertBytesToHexStringWithOffset() {
        byte[] b = new byte[]{(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};
        assertEquals("FE BA", ConvertHelper.byte2HexString(b, 1, 2));
    }

    @Test
    void shouldConvertBytesToHexStringWithoutSpace() {
        byte[] b = new byte[]{(byte) 0xCA, (byte) 0xFE};
        assertEquals("CAFE", ConvertHelper.byte2HexStringWithoutSpace(b));
    }

    @Test
    void shouldReturnNullForNullByteArrayWithoutSpace() {
        assertEquals("null", ConvertHelper.byte2HexStringWithoutSpace(null));
    }

    @Test
    void shouldConvertHexStringToBytes() {
        byte[] b = ConvertHelper.hexString2Byte("CA FE");
        assertNotNull(b);
        assertEquals(2, b.length);
        assertEquals((byte) 0xCA, b[0]);
        assertEquals((byte) 0xFE, b[1]);
    }

    @Test
    void shouldReturnNullForInvalidHexString() {
        byte[] b = ConvertHelper.hexString2Byte("XYZ");
        assertNull(b);
    }

    @Test
    void shouldConvertHexStringNoSpaceToBytes() {
        byte[] b = ConvertHelper.hexString2ByteNoSpace("CAFE");
        assertNotNull(b);
        assertEquals(2, b.length);
        assertEquals((byte) 0xCA, b[0]);
        assertEquals((byte) 0xFE, b[1]);
    }

    @Test
    void shouldConvertInputStreamToString() throws IOException {
        String input = "Hello World";
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
        assertEquals("Hello World", ConvertHelper.inputStream2String(bais));
    }

    @Test
    void shouldConvertStringToBytes() {
        byte[] b = ConvertHelper.string2Byte("test");
        assertNotNull(b);
        assertEquals(4, b.length);
    }

    @Test
    void shouldConvertBytesToString() {
        byte[] b = new byte[]{116, 101, 115, 116};
        assertEquals("test", ConvertHelper.byte2String(b));
    }

    @Test
    void shouldConvertIntToBytes() {
        byte[] b = ConvertHelper.int2Byte(1);
        assertNotNull(b);
        assertEquals(4, b.length);
        assertEquals(1, b[0]);
        assertEquals(0, b[1]);
    }
}
