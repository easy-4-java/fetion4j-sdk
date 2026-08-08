package net.apexes.fetion4j.core.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuddyGroupTest {

    @Test
    void shouldCreateWithIdAndName() {
        BuddyGroup group = new BuddyGroup(1, "Friends");
        assertEquals(1, group.getId());
        assertEquals("Friends", group.getName());
    }

    @Test
    void shouldReturnNameAsToString() {
        BuddyGroup group = new BuddyGroup(1, "Friends");
        assertEquals("Friends", group.toString());
    }

    @Test
    void shouldBeEqualById() {
        BuddyGroup g1 = new BuddyGroup(1, "Friends");
        BuddyGroup g2 = new BuddyGroup(1, "Different Name");
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void shouldNotBeEqualWithDifferentId() {
        BuddyGroup g1 = new BuddyGroup(1, "Friends");
        BuddyGroup g2 = new BuddyGroup(2, "Friends");
        assertNotEquals(g1, g2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        BuddyGroup group = new BuddyGroup(1, "Friends");
        assertNotEquals(null, group);
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        BuddyGroup group = new BuddyGroup(1, "Friends");
        assertNotEquals("string", group);
    }

    @Test
    void shouldImplementSerializable() {
        BuddyGroup group = new BuddyGroup(1, "Friends");
        assertTrue(group instanceof java.io.Serializable);
    }
}
