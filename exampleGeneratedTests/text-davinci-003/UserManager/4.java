import org.example.User;
import org.example.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserManager userManager;

    @BeforeEach
    public void setup() {
        userManager = new UserManager();
    }

    @Test
    public void createUser_valid_created() {
        // given
        final String username = "username";
        final String password = "password";

        // when
        final User user = userManager.createUser(username, password);

        // then
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void createUser_nullUsername_exceptionThrown() {
        // given
        final String username = null;
        final String password = "password";

        // when
        final Throwable throwable = assertThrows(IllegalArgumentException.class, () -> userManager.createUser(username, password));

        // then
        assertEquals("Username and password must not be null", throwable.getMessage());
    }

    @Test
    public void createUser_nullPassword_exceptionThrown() {
        // given
        final String username = "username";
        final String password = null;

        // when
        final Throwable throwable = assertThrows(IllegalArgumentException.class, () -> userManager.createUser(username, password));

        // then
        assertEquals("Username and password must not be null", throwable.getMessage());
    }

    @Test
    public void createUser_existingUser_exceptionThrown() {
        // given
        final String username = "existingUser";
        final String password = "password";

        userManager.createUser(username, password);

        // when
        final Throwable throwable = assertThrows(IllegalStateException.class, () -> userManager.createUser(username, password));

        // then
        assertEquals("User already exists", throwable.getMessage());
    }

    @Test
    public void getUser_valid_userRetrieved() {
        // given
        final String username = "username";
        final String password = "password";

        userManager.createUser(username, password);

        // when
        final User user = userManager.getUser(username);

        // then
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void getUser_nullUsername_exceptionThrown() {
        // given
        final String username = null;

        // when
        final Throwable throwable = assertThrows(IllegalArgumentException.class, () -> userManager.getUser(username));

        // then
        assertEquals("Username must not be null", throwable.getMessage());
    }

    @Test
    public void getUser_nonexistentUser_exceptionThrown() {
        // given
        final String username = "nonexistentUser";

        // when
        final Throwable throwable = assertThrows(IllegalStateException.class, () -> userManager.getUser(username));

        // then
        assertEquals("User does not exist", throwable.getMessage());
    }

    @Test
    public void deleteUser_valid_userDeleted() {
        // given
        final String username = "username";
        final String password = "password";

        userManager.createUser(username, password);

        // when
        userManager.deleteUser(username);

        // then
        final Throwable throwable = assertThrows(IllegalStateException.class, () -> userManager.getUser(username));
        assertEquals("User does not exist", throwable.getMessage());
    }

    @Test
    public void deleteUser_nullUsername_exceptionThrown() {
        // given
        final String username = null;

        // when
        final Throwable throwable = assertThrows(IllegalArgumentException.class, () -> userManager.deleteUser(username));

        // then
        assertEquals("Username must not be null", throwable.getMessage());
    }

    @Test
    public void deleteUser_nonexistentUser_exceptionThrown() {
        // given
        final String username = "nonexistentUser";

        // when
        final Throwable throwable = assertThrows(IllegalStateException.class, () -> userManager.deleteUser(username));

        // then
        assertEquals("User does not exist", throwable.getMessage());
    }
}