package net.apexes.fetion4j.core.sipc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SipcTest {

    @Test
    void shouldHaveCorrectVersion() {
        assertEquals("SIP-C/4.0", Sipc.SIPC_VERSION);
    }

    @Test
    void shouldHaveFieldConstants() {
        assertEquals("A", Sipc.FIELD_A);
        assertEquals("C", Sipc.FIELD_C);
        assertEquals("CL", Sipc.FIELD_CL);
        assertEquals("CN", Sipc.FIELD_CN);
        assertEquals("D", Sipc.FIELD_D);
        assertEquals("E", Sipc.FIELD_E);
        assertEquals("F", Sipc.FIELD_F);
        assertEquals("I", Sipc.FIELD_I);
        assertEquals("K", Sipc.FIELD_K);
        assertEquals("L", Sipc.FIELD_L);
        assertEquals("M", Sipc.FIELD_M);
        assertEquals("N", Sipc.FIELD_N);
        assertEquals("Q", Sipc.FIELD_Q);
        assertEquals("T", Sipc.FIELD_T);
        assertEquals("W", Sipc.FIELD_W);
        assertEquals("X", Sipc.FIELD_X);
    }

    @Test
    void shouldHaveMethodConstants() {
        assertEquals("R", Sipc.METHOD_R);
        assertEquals("M", Sipc.METHOD_M);
        assertEquals("S", Sipc.METHOD_S);
        assertEquals("SUB", Sipc.METHOD_SUB);
        assertEquals("BN", Sipc.METHOD_BN);
        assertEquals("A", Sipc.METHOD_A);
        assertEquals("B", Sipc.METHOD_B);
    }

    @Test
    void shouldHaveStatusConstants() {
        assertEquals(200, Sipc.STATUS_ACTION_OK);
        assertEquals(280, Sipc.STATUS_SEND_SMS_OK);
        assertEquals(400, Sipc.STATUS_BAD);
        assertEquals(401, Sipc.STATUS_UNAUTHORIZED);
        assertEquals(403, Sipc.STATUS_FORBIDDEN);
        assertEquals(404, Sipc.STATUS_NOT_FOUND);
        assertEquals(421, Sipc.STATUS_EXTENSION_REQUIRED);
        assertEquals(420, Sipc.STATUS_BAD_EXTENSION);
        assertEquals(500, Sipc.STATUS_SERVER_INTERNAL_ERROR);
        assertEquals(503, Sipc.STATUS_SERVER_UNAVAILABLE);
        assertEquals(504, Sipc.STATUS_TIME_OUT);
    }
}
