package net.apexes.fetion4j.core.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void shouldCreateEmptyUser() {
        User user = new User();
        assertEquals(0, user.getUserId());
        assertNull(user.getUri());
        assertNull(user.getName());
    }

    @Test
    void shouldCreateUserWithId() {
        User user = new User(12345);
        assertEquals(12345, user.getUserId());
    }

    @Test
    void shouldCreateUserWithAllArgs() {
        User user = new User(12345, "sip:12345@fetion.com.cn;p=100", "TestUser");
        assertEquals(12345, user.getUserId());
        assertEquals("sip:12345@fetion.com.cn;p=100", user.getUri());
        assertEquals("TestUser", user.getName());
    }

    @Test
    void shouldSetAndGetFields() {
        User user = new User();
        user.setUserId(99);
        user.setUri("sip:99@fetion.com.cn;p=1");
        user.setName("Name");
        assertEquals(99, user.getUserId());
        assertEquals("sip:99@fetion.com.cn;p=1", user.getUri());
        assertEquals("Name", user.getName());
    }

    @Test
    void shouldBeEqualByUserId() {
        User u1 = new User(100, "sip:100@fetion.com.cn;p=1", "A");
        User u2 = new User(100, "sip:100@fetion.com.cn;p=2", "B");
        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void shouldNotBeEqualWithDifferentUserId() {
        User u1 = new User(100);
        User u2 = new User(200);
        assertNotEquals(u1, u2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        User user = new User(100);
        assertNotEquals(null, user);
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        User user = new User(100);
        assertNotEquals("string", user);
    }

    @Test
    void shouldReturnMeaningfulToString() {
        User user = new User(12345, "sip:12345@fetion.com.cn;p=100", "Test");
        String str = user.toString();
        assertTrue(str.contains("12345"));
        assertTrue(str.contains("Test"));
    }
}
