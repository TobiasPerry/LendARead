package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoJpa implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User addUser(Behaviour behavior, String email, String name, String telephone, String password) {
        final User user = new User(email, name, telephone, password, behavior);
        user.setLocale(LocaleContextHolder.getLocale().getLanguage());
        em.persist(user);
        return user;
    }

    @Override
    public boolean changePassword(PasswordResetToken passwordResetToken, String newPassword) {
        User user = getUser(passwordResetToken.getUserId()).orElse(null);
        if (user == null) return false;
        user.setPassword(newPassword);
        em.persist(user);
        return true;
    }

    @Override
    public Optional<User> getUser(String email) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public boolean changeRole(String email, Behaviour behaviour) {
        User user = getUser(email).orElse(null);
        if (user == null) return false;
        user.setBehaviour(behaviour);
        em.persist(user);
        return true;
    }

    @Override
    public Optional<User> getUser(int id) {
        User user = em.find(User.class, (long) id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public PasswordResetToken setForgotPasswordToken(PasswordResetToken passwordResetToken) {
        em.persist(passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public Optional<PasswordResetToken> getPasswordRestToken(String token) {
        TypedQuery<PasswordResetToken> query = em.createQuery("SELECT p FROM PasswordResetToken p WHERE p.token = :token", PasswordResetToken.class);
        query.setParameter("token", token);
        List<PasswordResetToken> passwordResetTokenList = query.getResultList();
        return passwordResetTokenList.stream().findFirst();
    }

    @Override
    public int deletePasswordRestToken(String token) {
        return em.createQuery("delete from PasswordResetToken p where p.token=:token")
                .setParameter("token", token)
                .executeUpdate();
    }

    @Override
    public int deletePasswordRestToken(int userId) {
        return em.createQuery("delete from PasswordResetToken p where p.user=:userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public Optional<PasswordResetToken> getPasswordRestTokenOfUser(int userId) {
        TypedQuery<PasswordResetToken> query = em.createQuery("SELECT p FROM PasswordResetToken p WHERE p.user = :userId", PasswordResetToken.class);
        query.setParameter("userId", userId);
        List<PasswordResetToken> passwordResetTokenList = query.getResultList();
        return passwordResetTokenList.stream().findFirst();
    }

    @Override
    public void deletePasswordRecoveryTokensOnDay(LocalDate date) {
        em.createQuery("delete from PasswordResetToken p where  p.expiryDate = :date").setParameter("date", date).executeUpdate();
    }
}
