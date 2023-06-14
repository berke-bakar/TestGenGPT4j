import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Test
    void createUser_NullUsername_ThrowsIllegalArgumentException() {
        // Arrange
        String username = null;
        String password = "password";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userManager.createUser(username, password));
    }

    @Test
    void createUser_NullPassword_ThrowsIllegalArgumentException() {
        // Arrange
        String username = "username";
        String password = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userManager.createUser(username, password));
    }

    @Test
    void createUser_ExistingUsername_ThrowsIllegalStateException() {
        // Arrange
        String username = "username";
        String password = "password";

        userManager.createUser(username, password);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> userManager.createUser(username, password));
    }

    @Test
    void createUser_ValidUsernameAndPassword_CreatesUser() {
        // Arrange
        String username = "username";
        String password = "password";

        // Act
        User createdUser = userManager.createUser(username, password);

        // Assert
        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());
        assertEquals(password, createdUser.getPassword());
    }

    @Test
    void getUser_NullUsername_ThrowsIllegalArgumentException() {
        // Arrange
        String username = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userManager.getUser(username));
    }

    @Test
    void getUser_NotExistingUsername_ThrowsIllegalStateException() {
        // Arrange
        String username = "username";

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> userManager.getUser(username));
    }

    @Test
    void getUser_ExistingUsername_ReturnsUser() {
        // Arrange
        String username = "username";
        String password = "password";
        User newUser = new User(username, password);

        userManager.database.put(username, newUser);

        // Act
        User user = userManager.getUser(username);

        // Assert
        assertEquals(newUser, user);
    }

    @Test
    void deleteUser_NullUsername_ThrowsIllegalArgumentException() {
        // Arrange
        String username = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userManager.deleteUser(username));
    }

    @Test
    void deleteUser_NotExistingUsername_ThrowsIllegalStateException() {
        // Arrange
        String username = "username";

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> userManager.deleteUser(username));
    }

    @Test
    void deleteUser_ExistingUsername_RemovesUser() {
        // Arrange
        String username = "username";
        String password = "password";
        User user = new User(username, password);

        userManager.database.put(username, user);

        // Act
        userManager.deleteUser(username);

        // Assert
        assertFalse(userManager.database.containsKey(username));
    }
}