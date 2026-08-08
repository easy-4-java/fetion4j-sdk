package net.apexes.fetion4j.core.sipc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class SipcMessageTest {

    @Test
    void shouldCreateRequestMessage() {
        RequestMessage msg = new RequestMessage("R");
        assertEquals("R", msg.getMethod());
        assertEquals("fetion.com.cn", msg.getAcceptor());
    }

    @Test
    void shouldCreateRequestMessageWithAcceptor() {
        RequestMessage msg = new RequestMessage("R", "custom.host");
        assertEquals("custom.host", msg.getAcceptor());
    }

    @Test
    void shouldCreateResponseMessage() {
        ResponseMessage msg = new ResponseMessage(200, "OK");
        assertEquals(200, msg.getStatus());
        assertEquals("OK", msg.getStatusMessage());
    }

    @Test
    void shouldSetAndGetCallId() {
        RequestMessage msg = new RequestMessage("R");
        msg.setCallId(42);
        assertEquals(42, msg.getCallId());
    }

    @Test
    void shouldSetAndGetSequence() {
        RequestMessage msg = new RequestMessage("R");
        msg.setSequence(7);
        assertEquals(7, msg.getSequence());
    }

    @Test
    void shouldSetAndGetSliceOffset() {
        RequestMessage msg = new RequestMessage("R");
        assertEquals(-1, msg.getSliceOffset());
        msg.setSliceOffset(100);
        assertEquals(100, msg.getSliceOffset());
    }

    @Test
    void shouldSetAndGetBody() {
        RequestMessage msg = new RequestMessage("R");
        assertNull(msg.getBody());
        msg.setBody("test body");
        assertEquals("test body", msg.getBody());
    }

    @Test
    void shouldGetBodyLength() {
        RequestMessage msg = new RequestMessage("R");
        assertEquals(0, msg.getBodyLength());
        msg.setBody("hello");
        assertEquals(5, msg.getBodyLength());
    }

    @Test
    void shouldAddAndSetFields() {
        RequestMessage msg = new RequestMessage("R");
        msg.addField("F", "123456789");
        msg.addField("F", "987654321");
        List<String> values = msg.getFieldValues("F");
        assertEquals(2, values.size());

        msg.setField("F", "111111111");
        values = msg.getFieldValues("F");
        assertEquals(1, values.size());
        assertEquals("111111111", values.get(0));
    }

    @Test
    void shouldRemoveField() {
        RequestMessage msg = new RequestMessage("R");
        msg.addField("F", "123456789");
        msg.addField("F", "987654321");
        msg.removeField("F");
        assertEquals(1, msg.getFieldValues("F").size());
    }

    @Test
    void shouldRemoveAllFields() {
        RequestMessage msg = new RequestMessage("R");
        msg.addField("F", "123456789");
        msg.addField("F", "987654321");
        msg.removeAllField("F");
        assertTrue(msg.getFieldValues("F").isEmpty());
    }

    @Test
    void shouldCheckHasField() {
        RequestMessage msg = new RequestMessage("R");
        assertFalse(msg.hasField("F"));
        msg.addField("F", "123456789");
        assertTrue(msg.hasField("F"));
    }

    @Test
    void shouldGetFieldValue() {
        RequestMessage msg = new RequestMessage("R");
        assertNull(msg.getFieldValue("F"));
        msg.addField("F", "123456789");
        assertEquals("123456789", msg.getFieldValue("F"));
    }

    @Test
    void shouldGenerateRequestHeadline() {
        RequestMessage msg = new RequestMessage("R");
        String text = msg.getText();
        assertTrue(text.startsWith("R fetion.com.cn SIP-C/4.0"));
    }

    @Test
    void shouldGenerateResponseHeadline() {
        ResponseMessage msg = new ResponseMessage(200, "OK");
        String text = msg.getText();
        assertTrue(text.startsWith("SIP-C/4.0 200 OK"));
    }

    @Test
    void shouldIncludeBodyInText() {
        RequestMessage msg = new RequestMessage("R");
        msg.setCallId(1);
        msg.setSequence(1);
        msg.setBody("test");
        String text = msg.getText();
        assertTrue(text.contains("L: 4"));
        assertTrue(text.contains("test"));
    }

    @Test
    void shouldIncludeSliceOffsetInText() {
        RequestMessage msg = new RequestMessage("R");
        msg.setCallId(1);
        msg.setSequence(1);
        msg.setSliceOffset(100);
        msg.setBody("test");
        String text = msg.getText();
        assertTrue(text.contains("L: 4;p=100"));
    }

    @Test
    void shouldGenerateEmptyBodyText() {
        RequestMessage msg = new RequestMessage("R");
        msg.setCallId(1);
        msg.setSequence(1);
        String text = msg.getText();
        assertFalse(text.contains("L:"));
    }

    @Test
    void shouldSetAndGetRequestMessageOnResponse() {
        ResponseMessage resp = new ResponseMessage(200, "OK");
        RequestMessage req = new RequestMessage("R");
        resp.setRequestMessage(req);
        assertEquals(req, resp.getRequestMessage());
    }

    @Test
    void shouldUseConstantSeparator() {
        assertEquals("\r\n", SipcMessage.SEPARATOR);
    }

    @Test
    void toStringShouldDelegateToGetText() {
        RequestMessage msg = new RequestMessage("R");
        assertEquals(msg.getText(), msg.toString());
    }
}
