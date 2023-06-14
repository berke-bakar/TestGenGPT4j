package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserManager {

    private final Map<String, User> database = new HashMap<>();

    public User createUser(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }

        if (database.containsKey(username)) {
            throw new IllegalStateException("User already exists");
        }

        User user = new User(username, password);
        database.put(username, user);
        return user;
    }

    public User getUser(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null");
        }

        User user = database.get(username);
        if (user == null) {
            throw new IllegalStateException("User does not exist");
        }

        return user;
    }

    public void deleteUser(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null");
        }

        if (!database.containsKey(username)) {
            throw new IllegalStateException("User does not exist");
        }

        database.remove(username);
    }

    public boolean containsValue(User user){
        return database.containsValue(user);
    }
}

class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}