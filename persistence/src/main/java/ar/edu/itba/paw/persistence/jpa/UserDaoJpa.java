package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
@Repository
public class UserDaoJpa implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User addUser(Behaviour behavior, String email, String name, String telephone, String password) {
        final UserImpl user = new UserImpl(email, name, telephone, behavior, password);
        em.persist(user);
        return user;
    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        return false;
    }

    @Override
    public Optional<User> getUser(String email) {
        return Optional.empty();
    }

    @Override
    public boolean changeRole(String email, Behaviour behaviour) {
        return false;
    }

    @Override
    public Optional<User> getUser(int id) {
        return Optional.empty();
    }

    @Override
    public boolean setForgotPasswordToken(PasswordResetToken passwordResetToken) {
        return false;
    }

    @Override
    public Optional<PasswordResetToken> getPasswordRestToken(String token) {
        return Optional.empty();
    }

    @Override
    public int deletePasswordRestToken(String token) {
        return 0;
    }
}
