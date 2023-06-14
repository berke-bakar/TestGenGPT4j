import org.example.User;
import org.example.UserManager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @Test
    void testCreateUser_NullUsername() {
        String username = null;
        String password = "password1";

        UserManager userManager = new UserManager();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userManager.createUser(username, password));
        assertEquals("Username and password must not be null", exception.getMessage());
    }

    @Test
    void testCreateUser_NullPassword() {
        String username = "username1";
        String password = null;

        UserManager userManager = new UserManager();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userManager.createUser(username, password));
        assertEquals("Username and password must not be null", exception.getMessage());
    }

    @Test
    void testCreateUser_ExistingUser() {
        String username = "username2";
        String password = "password2";
        User user = new User(username, password);

        UserManager userManager = new UserManager();
        userManager.database.put(username, user);

        Exception exception = assertThrows(IllegalStateException.class, () -> userManager.createUser(username, password));
        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void testCreateUser_Success() {
        String username = "username3";
        String password = "password3";
        User expectedUser = new User(username, password);

        UserManager userManager = new UserManager();
        User actualUser = userManager.createUser(username, password);

        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    void testGetUser_NullUsername() {
        String username = null;

        UserManager userManager = new UserManager();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userManager.getUser(username));
        assertEquals("Username must not be null", exception.getMessage());
    }

    @Test
    void testGetUser_NonExistingUser() {
        String username = "username4";

        UserManager userManager = new UserManager();
        Exception exception = assertThrows(IllegalStateException.class, () -> userManager.getUser(username));
        assertEquals("User does not exist", exception.getMessage());
    }

    @Test
    void testGetUser_Success() {
        String username = "username5";
        String password = "password5";
        User expectedUser = new User(username, password);

        UserManager userManager = new UserManager();
        userManager.database.put(username, expectedUser);

        User actualUser = userManager.getUser(username);

        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    void testDeleteUser_NullUsername() {
        String username = null;

        UserManager userManager = new UserManager();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userManager.deleteUser(username));
        assertEquals("Username must not be null", exception.getMessage());
    }

    @Test
    void testDeleteUser_NonExistingUser() {
        String username = "username6";

        UserManager userManager = new UserManager();
        Exception exception = assertThrows(IllegalStateException.class, () -> userManager.deleteUser(username));
        assertEquals("User does not exist", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        String username = "username7";
        String password = "password7";
        User user = new User(username, password);

        UserManager userManager = new UserManager();
        userManager.database.put(username, user);

        userManager.deleteUser(username);

        assertFalse(userManager.database.containsKey(username));
    }
}