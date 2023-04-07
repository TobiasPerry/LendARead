package models.userContext.implementations;

import models.userContext.interfaces.User;

public class UserImpl implements User {
    private final String email;
    private final String name;
    private final String message;

    public UserImpl(String email, String name, String message) {
        this.email = email;
        this.name = name;
        this.message = message;
    }
}

