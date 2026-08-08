package net.apexes.fetion4j.core.client.transfer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TransferExceptionTest {

    @Test
    void shouldCreateWithMessage() {
        TransferException ex = new TransferException("connection failed");
        assertEquals("connection failed", ex.getMessage());
    }

    @Test
    void shouldCreateWithCause() {
        Exception cause = new RuntimeException("root");
        TransferException ex = new TransferException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void shouldCreateWithMessageAndCause() {
        Exception cause = new RuntimeException("root");
        TransferException ex = new TransferException("wrapped", cause);
        assertEquals("wrapped", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void shouldCreateWithNoArgs() {
        TransferException ex = new TransferException();
        assertNull(ex.getMessage());
    }

    @Test
    void shouldExtendException() {
        TransferException ex = new TransferException("test");
        assertTrue(ex instanceof Exception);
    }
}
