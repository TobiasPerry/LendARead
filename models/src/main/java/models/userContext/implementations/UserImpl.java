package models.userContext.implementations;

import models.userContext.interfaces.User;

final public class UserImpl implements User {
    private final String email;
    private final String name;
    private final String message;

    public UserImpl(String email, String name, String message) {
        this.email = email;
        this.name = name;
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

