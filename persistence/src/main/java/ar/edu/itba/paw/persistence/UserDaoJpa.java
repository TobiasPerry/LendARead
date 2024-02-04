package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.Behaviour;
import ar.edu.itba.paw.models.userContext.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
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
    public User addUser(Behaviour behavior, String email, String name, String telephone, String password,String locale) {
        final User user = new User(email, name, telephone, password, behavior,locale);
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
    public Optional<User> getUser(int id) {
        User user = em.find(User.class, (long) id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public void setForgotPasswordToken(PasswordResetToken passwordResetToken) {
        em.persist(passwordResetToken);
    }

    @Override
    public Optional<PasswordResetToken> getPasswordRestToken(String token) {
        TypedQuery<PasswordResetToken> query = em.createQuery("SELECT p FROM PasswordResetToken p WHERE p.token = :token", PasswordResetToken.class);
        query.setParameter("token", token);
        List<PasswordResetToken> passwordResetTokenList = query.getResultList();
        return passwordResetTokenList.stream().findFirst();
    }

    @Override
    public void deletePasswordRestToken(String token) {
        em.createQuery("delete from PasswordResetToken p where p.token=:token")
                .setParameter("token", token)
                .executeUpdate();
    }

    @Override
    public void deletePasswordRestToken(int userId) {
        em.createQuery("delete from PasswordResetToken p where p.user=:userId")
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
