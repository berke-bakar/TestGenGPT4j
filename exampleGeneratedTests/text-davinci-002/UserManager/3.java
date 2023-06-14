@Test
public void testCreateUser() {
    UserManager manager = new UserManager();
    manager.createUser("John", "123456");
    assertEquals(1, manager.getUsers().size());
}

@Test
public void testCreateUser_nullUsername() {
    UserManager manager = new UserManager();
    assertThrows(IllegalArgumentException.class, () ->
        manager.createUser(null, "123456"));
}

@Test
public void testCreateUser_nullPassword() {
    UserManager manager = new UserManager();
    assertThrows(IllegalArgumentException.class, () ->
            manager.createUser("John", null));
}

@Test
public void testCreateUser_existingUser() {
    UserManager manager = new UserManager();
    manager.createUser("John", "123456");
    assertThrows(IllegalStateException.class, () ->
        manager.createUser("John", "123456"));
}

@Test
public void testGetUser() {
    UserManager manager = new UserManager();
    manager.createUser("John", "123456");
    User user = manager.getUser("John");
    assertEquals("John", user.getUsername());
    assertEquals("123456", user.getPassword());
}

@Test
public void testGetUser_nullUsername() {
    UserManager manager = new UserManager();
    assertThrows(IllegalArgumentException.class, () ->
            manager.getUser(null));
}

@Test
public void testGetUser_nonExistingUser() {
    UserManager manager = new UserManager();
    assertThrows(IllegalStateException.class, () ->
            manager.getUser("John"));
}

@Test
public void testDeleteUser() {
    UserManager manager = new UserManager();
    manager.createUser("John", "123456");
    manager.deleteUser("John");
    assertThrows(IllegalStateException.class, () ->
        manager.getUser("John"));
}

@Test
public void testDeleteUser_nullUsername() {
    UserManager manager = new UserManager();
    assertThrows(IllegalArgumentException.class, () ->
        manager.deleteUser(null));
}

@Test
public void testDeleteUser_nonExistingUser() {
    UserManager manager = new UserManager();
    assertThrows(IllegalStateException.class, () ->
        manager.deleteUser("John"));
}