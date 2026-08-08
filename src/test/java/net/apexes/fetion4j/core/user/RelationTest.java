package net.apexes.fetion4j.core.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RelationTest {

    @Test
    void shouldReturnCorrectValues() {
        assertEquals(0, Relation.UNCONFIRMED.getValue());
        assertEquals(1, Relation.BUDDY.getValue());
        assertEquals(2, Relation.DECLINED.getValue());
        assertEquals(3, Relation.STRANGER.getValue());
        assertEquals(4, Relation.BANNED.getValue());
    }

    @Test
    void shouldMapFromIntValues() {
        assertEquals(Relation.UNCONFIRMED, Relation.valueOf(0));
        assertEquals(Relation.BUDDY, Relation.valueOf(1));
        assertEquals(Relation.DECLINED, Relation.valueOf(2));
        assertEquals(Relation.STRANGER, Relation.valueOf(3));
        assertEquals(Relation.BANNED, Relation.valueOf(4));
    }

    @Test
    void shouldThrowForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> Relation.valueOf(99));
    }

    @Test
    void shouldThrowForNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> Relation.valueOf(-1));
    }
}
