package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetTokenImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import java.util.Optional;

public interface UserDao {
     UserImpl addUser(Behaviour behavior, String email, String name, String telephone, String password);
    boolean changePassword(PasswordResetTokenImpl passwordResetToken,String newPassword);
     Optional<UserImpl> getUser(String email);

     boolean changeRole(String email,Behaviour behaviour);

     Optional<UserImpl> getUser(int id);
    PasswordResetTokenImpl setForgotPasswordToken(PasswordResetTokenImpl passwordResetToken);
    Optional<PasswordResetTokenImpl> getPasswordRestToken(String token);
    int deletePasswordRestToken(String token);
}
