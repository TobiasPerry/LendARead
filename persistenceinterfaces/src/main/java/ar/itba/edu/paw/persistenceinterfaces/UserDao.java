package ar.itba.edu.paw.persistenceinterfaces;

import models.userContext.interfaces.User;

import java.util.Optional;

public interface UserDao {
    Optional<Integer> addUser(final User us);
}
