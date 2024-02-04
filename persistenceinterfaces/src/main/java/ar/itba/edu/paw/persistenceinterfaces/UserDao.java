package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.Behaviour;
import ar.edu.itba.paw.models.userContext.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.User;

import java.time.LocalDate;
import java.util.Optional;

public interface UserDao {
    User addUser(Behaviour behavior, String email, String name, String telephone, String password,String locale);

    boolean changePassword(PasswordResetToken passwordResetToken, String newPassword);

    Optional<User> getUser(String email);

    Optional<User> getUser(int id);

    void setForgotPasswordToken(PasswordResetToken passwordResetToken);

    Optional<PasswordResetToken> getPasswordRestToken(String token);

    void deletePasswordRestToken(String token);

    void deletePasswordRestToken(int userId);
    Optional<PasswordResetToken> getPasswordRestTokenOfUser (int userId);

    void deletePasswordRecoveryTokensOnDay(LocalDate data);
}
