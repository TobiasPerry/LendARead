package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String email);
     int createUser(String email,String name,String telephone,String password);

     boolean changeRole(String email,Behaviour behaviour);

     String getCurrentUser();


    Collection
            <? extends GrantedAuthority> getCurrentRoles();
    boolean createChangePasswordToken(String email);
    boolean changePassword(String token,String password);
    boolean isTokenValid(String token);
}
