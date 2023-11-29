package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserService {
    UserImpl getUser(final String email) throws UserNotFoundException;

    UserImpl getUserById(final int id) throws UserNotFoundException;

    UserImpl createUser(final String email, final String name, final String telephone, final String password);

    void changeRole(final String email, final Behaviour behaviour) throws UserNotFoundException;

    boolean getCurrentUserIsBorrower();

    String getCurrentUser();

    Collection
            <? extends GrantedAuthority> getCurrentRoles();

    void createChangePasswordToken(final String email) throws UserNotFoundException;

    void changePassword(final String email,final String token, final String password);

    boolean isTokenValid(final String token);

    void logInUser(final String email, final String password);

    boolean isCurrent(int userId);

    int changeUserProfilePic(final String email, byte[] parsedImage) throws UserNotFoundException;

    @Scheduled
    void deletePastChangePasswordTokens();
}
