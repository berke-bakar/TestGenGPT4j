import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class UserManagerTest {

    private UserManager userManager;
    private String username;
    private String password;
    private User user;

    @Test
    void createUser_nullUsername_IllegalArgumentException() {
        userManager = new UserManager();
        username = null;
        password = "123456";

        assertThrows(IllegalArgumentException.class, () -> userManager.createUser(username, password));
    }

    @Test
    void createUser_nullPassword_IllegalArgumentException() {
        userManager = new UserManager();
        username = "john";
        password = null;

        assertThrows(IllegalArgumentException.class, () -> userManager.createUser(username, password));
    }

    @Test
    void createUser_existingUser_IllegalStateException() {
        userManager = new UserManager();
        username = "john";
        password = "123456";

        userManager.createUser(username, password);
        assertThrows(IllegalStateException.class, () -> userManager.createUser(username, password));
    }

    @Test
    void createUser_validData_userCreated() {
        userManager = new UserManager();
        username = "john";
        password = "123456";

        user = userManager.createUser(username, password);
        assertNotNull(user);
    }

    @Test
    void getUser_nullUsername_IllegalArgumentException() {
        userManager = new UserManager();
        username = null;

        assertThrows(IllegalArgumentException.class, () -> userManager.getUser(username));
    }

    @Test
    void getUser_nonExistentUser_IllegalStateException() {
        userManager = new UserManager();
        username = "john";

        assertThrows(IllegalStateException.class, () -> userManager.getUser(username));
    }

    @Test
    void getUser_validData_userReturned() {
        userManager = new UserManager();
        username = "john";
        password = "123456";
        userManager.createUser(username, password);

        user = userManager.getUser(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    void deleteUser_nullUsername_IllegalArgumentException() {
        userManager = new UserManager();
        username = null;

        assertThrows(IllegalArgumentException.class, () -> userManager.deleteUser(username));
    }

    @Test
    void deleteUser_nonExistentUser_IllegalStateException() {
        userManager = new UserManager();
        username = "john";

        assertThrows(IllegalStateException.class, () -> userManager.deleteUser(username));
    }

    @Test
    void deleteUser_validData_userDeleted() {
        userManager = new UserManager();
        username = "john";
        password = "123456";
        userManager.createUser(username, password);

        userManager.deleteUser(username);

        assertThrows(IllegalStateException.class, () -> userManager.getUser(username));
    }
}