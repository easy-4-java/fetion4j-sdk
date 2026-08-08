package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FetionExceptionTest {

    @Test
    void shouldCreateWithMessage() {
        FetionException ex = new FetionException("test error");
        assertEquals("test error", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void shouldCreateWithCause() {
        Exception cause = new RuntimeException("root");
        FetionException ex = new FetionException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    void shouldCreateWithMessageAndCause() {
        Exception cause = new RuntimeException("root");
        FetionException ex = new FetionException("wrapped", cause);
        assertEquals("wrapped", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void shouldCreateWithNoArgs() {
        FetionException ex = new FetionException();
        assertNull(ex.getMessage());
    }
}
