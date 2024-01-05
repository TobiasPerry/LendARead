package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.implementations.User;

import java.time.LocalDate;
import java.util.Optional;

public interface UserDao {
    User addUser(Behaviour behavior, String email, String name, String telephone, String password);

    boolean changePassword(PasswordResetToken passwordResetToken, String newPassword);

    Optional<User> getUser(String email);

    boolean changeRole(String email, Behaviour behaviour);

    Optional<User> getUser(int id);

    PasswordResetToken setForgotPasswordToken(PasswordResetToken passwordResetToken);

    Optional<PasswordResetToken> getPasswordRestToken(String token);

    int deletePasswordRestToken(String token);

    int deletePasswordRestToken(int userId);
    Optional<PasswordResetToken> getPasswordRestTokenOfUser (int userId);

    void deletePasswordRecoveryTokensOnDay(LocalDate data);
}
