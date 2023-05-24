package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class UserReviewsDaoJpa implements UserReviewsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addReview(final String review, final int rating, final int lendingId, final int reviewerId, final int recipientId) {
        UserReview newReview = new UserReview(review, rating, lendingId, reviewerId, recipientId);
        em.persist(newReview);
    }

    @Override
    public double getRating(final int userId) {
        try {
            String jql = "SELECT AVG(r.rating) FROM UserReview r WHERE r.recipientId = :userId";
            return (Double) em.createQuery(jql)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0.0;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserReview> getUserReviewsAsRecipient(final int recipientId) {
        String jql = "SELECT r FROM UserReview r WHERE r.recipientId = :userId";
        return (List<UserReview>) em.createQuery(jql).setParameter("userId", recipientId).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserReview> getUserReviewsAsReviewer(final int reviewerId) {
        String jql = "SELECT r FROM UserReview r WHERE r.reviewerId = :userId";
        return (List<UserReview>) em.createQuery(jql).setParameter("userId", reviewerId).getResultList();
    }
}
