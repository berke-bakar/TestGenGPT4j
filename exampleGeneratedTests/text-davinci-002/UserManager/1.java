import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class UserManagerTest {

    private final UserManager userManager = new UserManager();

    @Test
    void testCreateUser() {
        // Sad path: username is null
        assertThrows(
                IllegalArgumentException.class,
                () -> userManager.createUser(null, "password"),
                "Username must not be null");

        // Sad path: password is null
        assertThrows(
                IllegalArgumentException.class,
                () -> userManager.createUser("username", null),
                "Password must not be null");

        // Sad path: user already exists
        userManager.createUser("username", "password");
        assertThrows(
                IllegalStateException.class,
                () -> userManager.createUser("username", "password"),
                "User already exists");
    }

    @Test
    void testGetUser() {
        // Sad path: username is null
        assertThrows(
                IllegalArgumentException.class,
                () -> userManager.getUser(null),
                "Username must not be null");

        // Sad path: user does not exist
        assertThrows(
                IllegalStateException.class,
                () -> userManager.getUser("username"),
                "User does not exist");
    }

    @Test
    void testDeleteUser() {
        // Sad path: username is null
        assertThrows(
                IllegalArgumentException.class,
                () -> userManager.deleteUser(null),
                "Username must not be null");

        // Sad path: user does not exist
        assertThrows(
                IllegalStateException.class,
                () -> userManager.deleteUser("username"),
                "User does not exist");
    }
}