package net.apexes.fetion4j.core.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PresenceTest {

    @Test
    void shouldReturnCorrectValueForOnline() {
        assertEquals(400, Presence.ONLINE.getValue());
    }

    @Test
    void shouldReturnCorrectValueForOffline() {
        assertEquals(0, Presence.OFFLINE.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBusy() {
        assertEquals(600, Presence.BUSY.getValue());
    }

    @Test
    void shouldReturnCorrectValueForAway() {
        assertEquals(100, Presence.AWAY.getValue());
    }

    @Test
    void shouldReturnCorrectValueForRobot() {
        assertEquals(499, Presence.ROBOT.getValue());
    }

    @Test
    void shouldReturnUnknownForNegativeValue() {
        assertEquals(-1, Presence.UNKNOWN.getValue());
    }

    @Test
    void shouldReturnOnlineFor400() {
        assertEquals(Presence.ONLINE, Presence.valueOf(400));
    }

    @Test
    void shouldReturnOfflineFor0() {
        assertEquals(Presence.OFFLINE, Presence.valueOf(0));
    }

    @Test
    void shouldReturnBusyFor600() {
        assertEquals(Presence.BUSY, Presence.valueOf(600));
    }

    @Test
    void shouldReturnAwayFor100() {
        assertEquals(Presence.AWAY, Presence.valueOf(100));
    }

    @Test
    void shouldReturnRobotFor499() {
        assertEquals(Presence.ROBOT, Presence.valueOf(499));
    }

    @Test
    void shouldReturnUnknownForUnmappedValue() {
        assertEquals(Presence.UNKNOWN, Presence.valueOf(999));
    }

    @Test
    void shouldReturnUnknownForNegativeValue() {
        assertEquals(Presence.UNKNOWN, Presence.valueOf(-100));
    }
}
