package ar.edu.itba.paw.models.userContext.factories;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

final public class UserFactory {
    public static UserImpl createUser(int id, String email, String name, String message, String password, Behaviour behaviour) {
        return new UserImpl(id,email, name, message,password,behaviour);
    }
    public static UserImpl createUser( String email, String name, String message, String password, Behaviour behaviour) {
        return new UserImpl(email, name, message,password,behaviour);
    }
}


