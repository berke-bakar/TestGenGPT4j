import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserManagerTest {

    private UserManager manager;

    @BeforeEach
    void setUp() {
        manager = new UserManager();
    }

    @Test
    void createUser_withValidArguments_returnsUser() {
        User user = manager.createUser("username", "password");

        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void createUser_withNullUsername_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> manager.createUser(null, "password"));
    }

    @Test
    void createUser_withNullPassword_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> manager.createUser("username", null));
    }

    @Test
    void createUser_withExistingUsername_throwsIllegalStateException() {
        manager.createUser("username", "password");

        assertThrows(IllegalStateException.class, () -> manager.createUser("username", "password"));
    }

    @Test
    void getUser_withValidArgument_returnsUser() {
        User expected = manager.createUser("username", "password");

        User actual = manager.getUser("username");

        assertEquals(expected, actual);
    }

    @Test
    void getUser_withNullUsername_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> manager.getUser(null));
    }

    @Test
    void getUser_withNonExistentUsername_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> manager.getUser("username"));
    }

    @Test
    void deleteUser_withValidArgument_deletesUser() {
        manager.createUser("username", "password");

        manager.deleteUser("username");

        assertThrows(IllegalStateException.class, () -> manager.getUser("username"));
    }

    @Test
    void deleteUser_withNullUsername_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> manager.deleteUser(null));
    }

    @Test
    void deleteUser_withNonExistentUsername_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> manager.deleteUser("username"));
    }
}