package ar.edu.itba.paw.models.userContext.factories;

import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.User;

final public class UserFactory {
    private UserFactory(){}
    public static User createUser(String email, String name, String message) {
        return new UserImpl(email, name, message);
    }
}


