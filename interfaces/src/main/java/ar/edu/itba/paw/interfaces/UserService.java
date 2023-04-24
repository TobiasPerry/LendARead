package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String email);
     int createUser(String email,String name,String telephone,String password);
}
