package models.userContext.factories;

import models.userContext.implementations.UserImpl;
import models.userContext.interfaces.User;

final public class UserFactory {
    private UserFactory(){}
    public static User createUser(String email, String name, String message) {
        return new UserImpl(email, name, message);
    }
}


