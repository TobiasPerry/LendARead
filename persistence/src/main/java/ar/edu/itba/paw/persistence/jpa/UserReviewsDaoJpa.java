package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public boolean getRating(int userId) {
        return false;
    }

    @Override
    public List<UserReview> getUserReviews(int userId) {
        return null;
    }
}
