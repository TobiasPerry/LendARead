package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserService {
    User getUser(final String email) throws UserNotFoundException;

    User getUserById(final int id) throws UserNotFoundException;

    User createUser(final String email, final String name, final String telephone, final String password);


    boolean getCurrentUserIsBorrower();

    String getCurrentUser();

    Collection
            <? extends GrantedAuthority> getCurrentRoles();

    void createChangePasswordToken(final int id) throws UserNotFoundException;

    void changePassword(final int id,final String token, final String password);

    boolean isTokenValid(final String token);


    int changeUserProfilePic(final int id, byte[] parsedImage) throws UserNotFoundException;

    void updateUser(final int id,final String username,final String telephone,final String role) throws UserNotFoundException;

    @Scheduled
    void deletePastChangePasswordTokens();
}
