package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
@Repository
public class UserDaoJpa implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User addUser(Behaviour behavior, String email, String name, String telephone, String password) {
        final User user = new UserImpl(email, name, telephone, behavior, password);
        em.persist(user);
        return user;
    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        User user = getUser(email).orElse(null);
        if(user == null) return false;
        user.setPassword(newPassword);
        em.persist(user);
        return true;
    }

    @Override
    public Optional<User> getUser(String email) {
        TypedQuery<UserImpl> query = em.createQuery("SELECT u FROM UserImpl u WHERE u.email = :email", UserImpl.class);
        query.setParameter("email", email);
        List<UserImpl> users = query.getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public boolean changeRole(String email, Behaviour behaviour) {
        User user = getUser(email).orElse(null);
        if(user == null) return false;
        user.setBehaviour(behaviour);
        em.persist(user);
        return true;
    }

    @Override
    public Optional<User> getUser(int id) {
        UserImpl user = em.find(UserImpl.class, (long) id);
        return user != null ? Optional.of(user) : Optional.empty();
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
