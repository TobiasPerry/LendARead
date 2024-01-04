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

    void createChangePasswordToken(final String email) throws UserNotFoundException;


    boolean isTokenValid(final int userId,final String token);

    String getUserResetPasswordToken(final String email) throws UserNotFoundException;

    int changeUserProfilePic(final int id, byte[] parsedImage) throws UserNotFoundException;

    void updateUser(final int id,final String username,final String telephone,final String role,final String password) throws UserNotFoundException;

    void deleteToken(final String token);
    @Scheduled
    void deletePastChangePasswordTokens();
}
