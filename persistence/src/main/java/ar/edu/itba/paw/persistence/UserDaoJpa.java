package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetTokenImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
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
    public UserImpl addUser(Behaviour behavior, String email, String name, String telephone, String password) {
        final UserImpl user = new UserImpl(email, name, telephone, password, behavior);
        user.setLocale(LocaleContextHolder.getLocale().getLanguage());
        em.persist(user);
        return user;
    }

    @Override
    public boolean changePassword(PasswordResetTokenImpl passwordResetToken, String newPassword) {
        UserImpl user = getUser(passwordResetToken.getUserId()).orElse(null);
        if (user == null) return false;
        user.setPassword(newPassword);
        em.persist(user);
        return true;
    }

    @Override
    public Optional<UserImpl> getUser(String email) {
        TypedQuery<UserImpl> query = em.createQuery("SELECT u FROM UserImpl u WHERE u.email = :email", UserImpl.class);
        query.setParameter("email", email);
        List<UserImpl> users = query.getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public boolean changeRole(String email, Behaviour behaviour) {
        UserImpl user = getUser(email).orElse(null);
        if (user == null) return false;
        user.setBehaviour(behaviour);
        em.persist(user);
        return true;
    }

    @Override
    public Optional<UserImpl> getUser(int id) {
        UserImpl user = em.find(UserImpl.class, (long) id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public PasswordResetTokenImpl setForgotPasswordToken(PasswordResetTokenImpl passwordResetToken) {
        em.persist(passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public Optional<PasswordResetTokenImpl> getPasswordRestToken(String token) {
        TypedQuery<PasswordResetTokenImpl> query = em.createQuery("SELECT p FROM PasswordResetTokenImpl p WHERE p.token = :token", PasswordResetTokenImpl.class);
        query.setParameter("token", token);
        List<PasswordResetTokenImpl> passwordResetTokenList = query.getResultList();
        return passwordResetTokenList.stream().findFirst();
    }

    @Override
    public int deletePasswordRestToken(String token) {
        return em.createQuery("delete from PasswordResetTokenImpl p where p.token=:token")
                .setParameter("token", token)
                .executeUpdate();
    }

    @Override
    public void deletePasswordRecoveryTokensOnDay(LocalDate date) {
        em.createQuery("delete from PasswordResetTokenImpl p where  p.expiryDate = :date").setParameter("date", date).executeUpdate();
    }
}
