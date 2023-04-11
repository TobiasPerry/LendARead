package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.Optional;

public interface UserDao {
    Optional<Integer> addUser(final User us);
}
