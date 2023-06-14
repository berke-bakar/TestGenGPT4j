import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class UserManagerTest {

    private UserManager manager = new UserManager();

    // Test for getUser()
    @Test
    void getUser_withValidUsername_shouldReturnUserObject() {
		String validUsername = "John_Smith";
        String validPassword = "password";

		User user = manager.createUser(validUsername, validPassword);
		User userFromManager = manager.getUser(validUsername);

        Assert.assertEquals(user, userFromManager);
    }

    // Test for getUser()
    @Test
    void getUser_withNonExistingUsername_shouldThrowIllegalStateException() {
		String nonExistingUsername = "John_Smith";

        Assert.assertThrows(IllegalStateException.class,
			() -> manager.getUser(nonExistingUsername));
    }

    // Test for createUser()
    @Test
    void createUser_withValidUsernameAndPassword_shouldCreateNewUser() {
		String validUsername = "John_Smith";
        String validPassword = "password";

        User newUser = manager.createUser(validUsername, validPassword);

        Assert.assertNotNull(newUser);
        Assert.assertEquals(validUsername, newUser.getUsername());
        Assert.assertEquals(validPassword, newUser.getPassword());
    }

    // Test for createUser()
    @Test
    void createUser_withExistingUsername_shouldThrowIllegalStateException() {
		String validUsername = "John_Smith";
        String validPassword = "password";

		manager.createUser(validUsername, validPassword);

        Assert.assertThrows(IllegalStateException.class,
			() -> manager.createUser(validUsername, validPassword));
    }

    // Test for createUser()
    @Test
    void createUser_withNullUsernameOrPassword_shouldThrowIllegalArgumentException() {
		String validUsername = "John_Smith";
        String validPassword = "password";

        Assert.assertThrows(IllegalArgumentException.class,
			() -> manager.createUser(null, validPassword));

		Assert.assertThrows(IllegalArgumentException.class,
			() -> manager.createUser(validUsername, null));
    }

    // Test for deleteUser()
    @Test
    void deleteUser_withValidUsername_shouldDeleteUser() {
		String validUsername = "John_Smith";
        String validPassword = "password";
		User user = manager.createUser(validUsername, validPassword);

		manager.deleteUser(validUsername);

        Assert.assertThrows(IllegalStateException.class,
			() -> manager.getUser(validUsername));
    }

    // Test for deleteUser()
    @Test
    void deleteUser_withNonExistingUsername_shouldThrowIllegalStateException() {
		String nonExistingUsername = "John_Smith";

        Assert.assertThrows(IllegalStateException.class,
			() -> manager.deleteUser(nonExistingUsername));
    }

    // Test for deleteUser()
    @Test
    void deleteUser_withNullUsername_shouldThrowIllegalArgumentException() {

        Assert.assertThrows(IllegalArgumentException.class,
			() -> manager.deleteUser(null));
    }

}