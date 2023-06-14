import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class UserManagerTest {

    private UserManager userManager;
    private Map<String, User> database;

    @BeforeEach
    void setUp() {
        database = new HashMap<>();
        userManager = new UserManager(database);
    }

    @Test
    void createUserTest() {
        User user = userManager.createUser("testUser", "password");
        Assertions.assertEquals(user, database.get("testUser"));
    }

    @Test
    void createUserNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userManager.createUser(null, null);
        });
    }

    @Test
    void createUserAlreadyExistsTest() {
        database.put("testUser", new User("testUser", "password"));
        Assertions.assertThrows(IllegalStateException.class, () -> {
            userManager.createUser("testUser", "password");
        });
    }

    @Test
    void getUserTest() {
        User expectedUser = new User("testUser", "password");
        database.put("testUser", expectedUser);
        User actualUser = userManager.getUser("testUser");
        Assertions.assertSame(expectedUser, actualUser);
    }

    @Test
    void getUserNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userManager.getUser(null);
        });
    }

    @Test
    void getUserDoesNotExistTest() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            userManager.getUser("testUser");
        });
    }

    @Test
    void deleteUserTest() {
        database.put("testUser", new User("testUser", "password"));
        userManager.deleteUser("testUser");
        Assertions.assertNull(database.get("testUser"));
    }

    @Test
    void deleteUserNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userManager.deleteUser(null);
        });
    }

    @Test
    void deleteUserDoesNotExistTest() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            userManager.deleteUser("testUser");
        });
    }
}