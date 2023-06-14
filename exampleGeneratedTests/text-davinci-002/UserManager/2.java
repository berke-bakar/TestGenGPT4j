import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserManagerTest {
 @Test
    void createUser_correctUsernameAndPassword_shouldCreateUser() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = "username";
        String password = "password";

        // Act
        User user = userManager.createUser(username, password);

        // Assert
        Assertions.assertNotNull(user);
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    void createUser_nullUsername_shouldThrowIllegalArgumentException() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = null;
        String password = "password";

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userManager.createUser(username, password));
    }

    @Test
    void createUser_nullPassword_shouldThrowIllegalArgumentException() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = "username";
        String password = null;

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userManager.createUser(username, password));
    }

    @Test
    void createUser_existingUsername_shouldThrowIllegalStateException() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = "username";
        String password = "password";
        User user = userManager.createUser(username, password);

        // Act & Assert
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> userManager.createUser(username, password));
    }

    @Test
    void getUser_correctUsername_shouldReturnUser() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = "username";
        String password = "password";
        User user = userManager.createUser(username, password);

        // Act
        User actualUser = userManager.getUser(username);

        // Assert
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(user, actualUser);
    }

    @Test
    void getUser_nullUsername_shouldThrowIllegalArgumentException() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = null;

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userManager.getUser(username));
    }

    @Test
    void getUser_nonExistingUsername_shouldThrowIllegalStateException() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = "username";

        // Act & Assert
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> userManager.getUser(username));
    }

    @Test
    void deleteUser_correctUsername_shouldDeleteUser() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = "username";
        String password = "password";
        User user = userManager.createUser(username, password);

        // Act
        userManager.deleteUser(username);

        // Assert
        Assertions.assertFalse(userManager.database.containsValue(user));
    }

    @Test
    void deleteUser_nullUsername_shouldThrowIllegalArgumentException() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = null;

        // Act & Assert
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userManager.deleteUser(username));
    }

    @Test
    void deleteUser_nonExistingUsername_shouldThrowIllegalStateException() {
        // Arrange
        UserManager userManager = new UserManager();
        String username = "username";

        // Act & Assert
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> userManager.deleteUser(username));
    }
}