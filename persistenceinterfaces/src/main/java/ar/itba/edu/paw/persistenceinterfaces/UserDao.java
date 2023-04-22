package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.Optional;

public interface UserDao {
    public Optional<Integer> addUser(String behavior,String email,String name,String telephone,String password);
    boolean changePassword(String email,String newPassword);
     Optional<User> getUser(String email);
}
