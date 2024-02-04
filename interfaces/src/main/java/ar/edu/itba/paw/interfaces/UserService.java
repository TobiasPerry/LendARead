package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.ImageNotExistException;
import ar.edu.itba.paw.exceptions.UnableToChangeRoleException;
import ar.edu.itba.paw.exceptions.UnableToCreateTokenException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.User;
import org.springframework.scheduling.annotation.Scheduled;

public interface UserService {
    User getUser(final String email) throws UserNotFoundException;

    User getUserById(final int id) throws UserNotFoundException;

    User createUser(final String email, final String name, final String telephone, final String password);

    User getCurrentUser() throws UserNotFoundException;

    void createChangePasswordToken(final String email) throws  UnableToCreateTokenException;

    boolean isTokenValid(final int userId,final String token);
    String getUserResetPasswordToken(final String email) throws UserNotFoundException;
    int changeUserProfilePic(final int id, byte[] parsedImage) throws UserNotFoundException;
    void updateUser(final int id,final String username,final String telephone,final String role,final String password,final Integer imageId) throws UserNotFoundException, UnableToChangeRoleException, ImageNotExistException;
    void deleteToken(final String token);
    @Scheduled
    void deletePastChangePasswordTokens();
}
