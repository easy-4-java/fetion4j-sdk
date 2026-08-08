package net.apexes.fetion4j.core.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuddyTest {

    @Test
    void shouldCreateEmptyBuddy() {
        Buddy buddy = new Buddy();
        assertEquals(0, buddy.getUserId());
        assertNull(buddy.getRelation());
    }

    @Test
    void shouldCreateBuddyWithArgs() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Name", Relation.BUDDY);
        assertEquals(12345, buddy.getUserId());
        assertEquals("sip:12345@fetion.com.cn;p=100", buddy.getUri());
        assertEquals("Name", buddy.getName());
        assertEquals(Relation.BUDDY, buddy.getRelation());
    }

    @Test
    void shouldSetAndGetRelation() {
        Buddy buddy = new Buddy();
        buddy.setRelation(Relation.STRANGER);
        assertEquals(Relation.STRANGER, buddy.getRelation());
    }

    @Test
    void shouldReturnMeaningfulToString() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        String str = buddy.toString();
        assertTrue(str.contains("Buddy{"));
        assertTrue(str.contains("12345"));
        assertTrue(str.contains("BUDDY"));
    }
}
