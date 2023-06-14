import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager manager;

    @BeforeEach
    void setUp() {
        manager = new UserManager();
    }

    @Test
    void createUser() {
        User user = manager.createUser("test", "test");
        assertEquals("test", user.getUsername());
    }

    @Test
    void getUser() {
        manager.createUser("test", "test");
        User user = manager.getUser("test");
        assertEquals("test", user.getUsername());
    }

    @Test
    void deleteUser() {
        manager.createUser("test", "test");
        manager.deleteUser("test");
        assertThrows(IllegalStateException.class, () -> manager.getUser("test"));
    }

    @Test
    void createUser_nullUsername() {
        assertThrows(IllegalArgumentException.class, () -> manager.createUser(null, "test"));
    }

    @Test
    void createUser_nullPassword() {
        assertThrows(IllegalArgumentException.class, () -> manager.createUser("test", null));
    }

    @Test
    void createUser_existingUser() {
        manager.createUser("test", "test");
        assertThrows(IllegalStateException.class, () -> manager.createUser("test", "test"));
    }

    @Test
    void getUser_nullUsername() {
        assertThrows(IllegalArgumentException.class, () -> manager.getUser(null));
    }

    @Test
    void getUser_nonExistingUser() {
        assertThrows(IllegalStateException.class, () -> manager.getUser("test"));
    }

    @Test
    void deleteUser_nullUsername() {
        assertThrows(IllegalArgumentException.class, () -> manager.deleteUser(null));
    }

    @Test
    void deleteUser_nonExistingUser() {
        assertThrows(IllegalStateException.class, () -> manager.deleteUser("test"));
    }
}