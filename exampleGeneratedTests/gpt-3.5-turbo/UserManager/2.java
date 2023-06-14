package org.example;

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
    void createUser_validArguments_returnsUser() {
        User user = userManager.createUser("testuser", "password1");
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("password1", user.getPassword());
    }

    @Test
    void createUser_nullArguments_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userManager.createUser(null, null));
    }

    @Test
    void createUser_existingUser_throwsIllegalStateException() {
        userManager.createUser("testuser", "password1");
        assertThrows(IllegalStateException.class, () -> userManager.createUser("testuser", "password2"));
    }

    @Test
    void getUser_existingUser_returnsUser() {
        User expectedUser = userManager.createUser("testuser", "password1");
        User actualUser = userManager.getUser("testuser");
        assertSame(expectedUser, actualUser);
    }

    @Test
    void getUser_nonexistentUser_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> userManager.getUser("testuser"));
    }

    @Test
    void getUser_nullArgument_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userManager.getUser(null));
    }

    @Test
    void deleteUser_existingUser_userRemovedFromDatabase() {
        userManager.createUser("testuser", "password1");
        assertTrue(userManager.database.containsKey("testuser"));
        userManager.deleteUser("testuser");
        assertFalse(userManager.database.containsKey("testuser"));
    }

    @Test
    void deleteUser_nonexistentUser_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> userManager.deleteUser("testuser"));
    }

    @Test
    void deleteUser_nullArgument_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userManager.deleteUser(null));
    }
}