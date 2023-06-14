import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Test
    void createUser_validInput_success() {
        User user = userManager.createUser("johndoe", "password");
        assertEquals("johndoe", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void createUser_nullUsername_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userManager.createUser(null, "password"));
    }

    @Test
    void createUser_nullPassword_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userManager.createUser("johndoe", null));
    }

    @Test
    void createUser_existingUser_throwsIllegalStateException() {
        userManager.createUser("johndoe", "password");
        assertThrows(IllegalStateException.class, () -> userManager.createUser("johndoe", "password"));
    }

    @Test
    void getUser_existingUser_success() {
        User user = userManager.createUser("johndoe", "password");
        User retrievedUser = userManager.getUser("johndoe");
        assertEquals(user, retrievedUser);
    }

    @Test
    void getUser_nonExistingUser_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> userManager.getUser("johndoe"));
    }

    @Test
    void getUser_nullUsername_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userManager.getUser(null));
    }

    @Test
    void deleteUser_existingUser_success() {
        userManager.createUser("johndoe", "password");
        userManager.deleteUser("johndoe");
        assertThrows(IllegalStateException.class, () -> userManager.getUser("johndoe"));
    }

    @Test
    void deleteUser_nonExistingUser_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> userManager.deleteUser("johndoe"));
    }

    @Test
    void deleteUser_nullUsername_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userManager.deleteUser(null));
    }
}