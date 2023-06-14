package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("UserManager Tests")
public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Nested
    @DisplayName("createUser method tests")
    class CreateUserTests {

        @Test
        @DisplayName("Creating a valid user")
        void testCreateUserValid() {
            User user = userManager.createUser("user1", "password1");
            Assertions.assertEquals("user1", user.getUsername());
            Assertions.assertEquals("password1", user.getPassword());
        }

        @Test
        @DisplayName("Creating a user with null username")
        void testCreateUserNullUsername() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> userManager.createUser(null, "password1"));
        }

        @Test
        @DisplayName("Creating a user with null password")
        void testCreateUserNullPassword() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> userManager.createUser("user1", null));
        }

        @Test
        @DisplayName("Creating a user with existing username")
        void testCreateUserExistingUsername() {
            userManager.createUser("user1", "password1");
            Assertions.assertThrows(IllegalStateException.class,
                    () -> userManager.createUser("user1", "password2"));
        }
    }

    @Nested
    @DisplayName("getUser method tests")
    class GetUserTests {

        private User user1;

        @BeforeEach
        void setUp() {
            user1 = userManager.createUser("user1", "password1");
        }

        @Test
        @DisplayName("Getting an existing user")
        void testGetUserExisting() {
            User user = userManager.getUser("user1");
            Assertions.assertEquals(user1, user);
        }

        @Test
        @DisplayName("Getting a non-existing user")
        void testGetUserNonExisting() {
            Assertions.assertThrows(IllegalStateException.class,
                    () -> userManager.getUser("user2"));
        }

        @Test
        @DisplayName("Getting a user with null username")
        void testGetUserNullUsername() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> userManager.getUser(null));
        }
    }

    @Nested
    @DisplayName("deleteUser method tests")
    class DeleteUserTests {

        private User user1;

        @BeforeEach
        void setUp() {
            user1 = userManager.createUser("user1", "password1");
        }

        @Test
        @DisplayName("Deleting an existing user")
        void testDeleteUserExisting() {
            userManager.deleteUser("user1");
            Assertions.assertThrows(IllegalStateException.class,
                    () -> userManager.getUser("user1"));
        }

        @Test
        @DisplayName("Deleting a non-existing user")
        void testDeleteUserNonExisting() {
            Assertions.assertThrows(IllegalStateException.class,
                    () -> userManager.deleteUser("user2"));
        }

        @Test
        @DisplayName("Deleting a user with null username")
        void testDeleteUserNullUsername() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> userManager.deleteUser(null));
        }
    }
}