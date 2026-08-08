package net.apexes.fetion4j.core.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactTest {

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact("100");
    }

    @Test
    void shouldCreateWithVersion() {
        assertEquals("100", contact.getVersion());
    }

    @Test
    void shouldSetAndGetVersion() {
        contact.setVersion("200");
        assertEquals("200", contact.getVersion());
    }

    @Test
    void shouldAddAndFindBuddy() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        contact.addBuddy(buddy);
        assertEquals(buddy, contact.findBuddy(12345));
        assertNull(contact.findBuddy(99999));
    }

    @Test
    void shouldAddBuddyWithVersion() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        contact.addBuddy(buddy, "200");
        assertEquals("200", contact.getVersion());
        assertEquals(buddy, contact.findBuddy(12345));
    }

    @Test
    void shouldFindBuddyByUri() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        contact.addBuddy(buddy);
        assertEquals(buddy, contact.findBuddy("sip:12345@fetion.com.cn;p=100"));
        assertNull(contact.findBuddy("sip:nonexistent@fetion.com.cn;p=1"));
    }

    @Test
    void shouldRemoveBuddy() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        contact.addBuddy(buddy);
        contact.removeBuddy(buddy, "201");
        assertNull(contact.findBuddy(12345));
        assertEquals("201", contact.getVersion());
    }

    @Test
    void shouldAddBuddyGroup() {
        BuddyGroup group = new BuddyGroup(1, "Friends");
        contact.addBuddyGroup(group);
        Collection<BuddyGroup> groups = contact.getBuddyGroups();
        assertEquals(1, groups.size());
        assertTrue(groups.contains(group));
    }

    @Test
    void shouldAddToBlacklist() {
        User user = new User(99999, "sip:99999@fetion.com.cn;p=100", "Blocked");
        contact.addBlacklist(user);
        Collection<User> blacklist = contact.getBlacklist();
        assertEquals(1, blacklist.size());
        assertTrue(blacklist.contains(user));
    }

    @Test
    void shouldReturnBuddys() {
        Buddy b1 = new Buddy(1, "sip:1@fetion.com.cn;p=1", "A", Relation.BUDDY);
        Buddy b2 = new Buddy(2, "sip:2@fetion.com.cn;p=2", "B", Relation.BUDDY);
        contact.addBuddy(b1);
        contact.addBuddy(b2);
        Collection<Buddy> buddys = contact.getBuddys();
        assertEquals(2, buddys.size());
    }

    @Test
    void shouldReturnMeaningfulToString() {
        Buddy buddy = new Buddy(12345, "sip:12345@fetion.com.cn;p=100", "Test", Relation.BUDDY);
        contact.addBuddy(buddy);
        String str = contact.toString();
        assertTrue(str.contains("100"));
        assertTrue(str.contains("Test"));
    }

    @Test
    void shouldImplementSerializable() {
        assertTrue(contact instanceof java.io.Serializable);
    }
}
