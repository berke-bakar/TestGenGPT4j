import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    private UserManager userManager;
    private final Map<String, User> database = new HashMap<>();

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Test
    void testCreateUser() {
        User user = userManager.createUser("testUser", "testPass");
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("testPass", user.getPassword());
        assertTrue(database.containsKey("testUser"));
    }

    @Test
    void testCreateUserInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> userManager.createUser(null, "testPass"));
        assertThrows(IllegalArgumentException.class, () -> userManager.createUser("testUser", null));
        assertFalse(database.containsKey("testUser"));
    }

    @Test
    void testCreateUserAlreadyExists() {
        database.put("testUser", new User("testUser", "testPass"));
        assertThrows(IllegalStateException.class, () -> userManager.createUser("testUser", "testPass"));
    }

    @Test
    void testGetUser() {
        database.put("testUser", new User("testUser", "testPass"));
        User user = userManager.getUser("testUser");
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("testPass", user.getPassword());
    }

    @Test
    void testGetUserInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> userManager.getUser(null));
    }

    @Test
    void testGetUserDoesNotExist() {
        assertThrows(IllegalStateException.class, () -> userManager.getUser("testUser"));
    }

    @Test
    void testDeleteUser() {
        database.put("testUser", new User("testUser", "testPass"));
        userManager.deleteUser("testUser");
        assertFalse(database.containsKey("testUser"));
    }

    @Test
    void testDeleteUserInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> userManager.deleteUser(null));
    }

    @Test
    void testDeleteUserDoesNotExist() {
        assertThrows(IllegalStateException.class, () -> userManager.deleteUser("testUser"));
    }
}