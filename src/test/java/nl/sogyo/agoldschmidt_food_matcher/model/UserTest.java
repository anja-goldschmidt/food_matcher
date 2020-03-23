package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user;

    @BeforeEach
    public void init() {
        user = new User();
    }

    @Test
    public void testUser() {
        assertNotNull(user);
    }

    @Test
    public void testName() {
        user.setName("First");
        assertEquals("First", user.getName());
    }

    @Test
    public void testUser_id() {
        user.setUserid(1);
        assertNotNull(user.getUserid());
        assertTrue(user.getUserid() instanceof Integer);
    }

    @Test
    public void testEmail() {
        user.setEmail("first@email.org");
        assertEquals("first@email.org", user.getEmail());
    }

    @Test
    public void testPassword() {
        user.setPassword("FirstPassword");
        assertEquals("FirstPassword", user.getPassword());
    }


}
