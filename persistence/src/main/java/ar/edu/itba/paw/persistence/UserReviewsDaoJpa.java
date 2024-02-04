package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.userContext.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserReviewsDaoJpa implements UserReviewsDao {


    private static Logger LOGGER = LoggerFactory.getLogger(UserReviewsDaoJpa.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addReview(final UserReview newReview) {
        em.persist(newReview);
    }


    @Override
    @SuppressWarnings("unchecked")
    public Optional<UserReview> getUserReviewsByLendingIdAndUser(final int lendingId, final String user) {
        final Query query = em.createQuery("SELECT r FROM UserReview r WHERE r.lending.id = :lendingId AND r.reviewer.email = :user");
        query.setParameter("lendingId", (long) lendingId);
        query.setParameter("user", user);
        final List<UserReview> list = query.getResultList();
        return list.stream().findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagingImpl<UserReview> getUserReviewsAsBorrower(int pageNum, int itemsPerPage, final User recipient) {

        final Query queryNative = em.createNativeQuery("SELECT r.id FROM userreview as r JOIN lendings l on l.id = r.lendid WHERE r.recipient = :userRecipient AND  l.borrowerid = r.recipient ORDER BY r.id DESC LIMIT :limit OFFSET :offset");

        final Query queryCount = em.createNativeQuery("SELECT count(r.id) FROM userreview as r JOIN lendings l on l.id = r.lendid  WHERE r.recipient = :userRecipient AND l.borrowerid = r.recipient");

        final int offset = (pageNum - 1) * itemsPerPage;

        queryCount.setParameter("userRecipient", recipient.getId());
        queryNative.setParameter("limit", itemsPerPage);
        queryNative.setParameter("offset", offset);
        queryNative.setParameter("userRecipient", recipient.getId());

        final int totalPages = (int) Math.ceil((double) ((Number) queryCount.getSingleResult()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (list.isEmpty())
            return new PagingImpl<>(Collections.emptyList(), pageNum, totalPages);
        return getReviewsFromIds(list, pageNum, totalPages);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage, final User reviewer) {

        final Query queryNative = em.createNativeQuery("SELECT r.id FROM userreview as r JOIN lendings l on l.id = r.lendid JOIN assetinstance a on a.id = l.assetinstanceid WHERE r.recipient = :userRecipient AND r.recipient = a.owner ORDER BY r.id DESC LIMIT :limit OFFSET :offset");

        final Query queryCount = em.createNativeQuery("SELECT count(r.id) FROM userreview as r JOIN lendings l on l.id = r.lendid JOIN assetinstance a on a.id = l.assetinstanceid WHERE r.recipient = :userRecipient AND r.recipient = a.owner");

        final int offset = (pageNum - 1) * itemsPerPage;

        queryCount.setParameter("userRecipient", reviewer.getId());
        queryNative.setParameter("limit", itemsPerPage);
        queryNative.setParameter("offset", offset);
        queryNative.setParameter("userRecipient", reviewer.getId());

        final int totalPages = (int) Math.ceil((double) ((Number) queryCount.getSingleResult()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (list.isEmpty())
            return new PagingImpl<>(Collections.emptyList(), pageNum, totalPages);
        return getReviewsFromIds(list, pageNum, totalPages);
    }

    @Override
    public Optional<UserReview> getUserReviewAsLender(int userId, int reviewId) {
        final  TypedQuery<UserReview>  query = em.createQuery("SELECT r FROM UserReview r WHERE r.recipient.id = :userId AND r.lending.assetInstance.userReference.id = :userId AND r.id = :reviewId",UserReview.class);
        query.setParameter("userId", (long) userId);
        query.setParameter("reviewId", (long) reviewId);
        List<UserReview> userReviews = query.getResultList();
        return userReviews.stream().findFirst();
    }

    @Override
    public Optional<UserReview> getUserReviewAsBorrower(int userId, int reviewId) {
        final  TypedQuery<UserReview>  query = em.createQuery("SELECT r FROM UserReview r WHERE r.recipient.id = :userId AND r.lending.userReference.id = :userId AND r.id = :reviewId",UserReview.class);
        query.setParameter("userId", (long) userId);
        query.setParameter("reviewId", (long) reviewId);
        List<UserReview> userReviews = query.getResultList();
        return userReviews.stream().findFirst();    }

    private PagingImpl<UserReview> getReviewsFromIds(final List<Long> list, final int pageNum, final int totalPages) {
        final TypedQuery<UserReview> query = em.createQuery("FROM UserReview AS ai WHERE id IN (:ids) ORDER BY ai.id DESC", UserReview.class);
        query.setParameter("ids", list);
        List<UserReview> reviewList = query.getResultList();
        return new PagingImpl<>(reviewList, pageNum, totalPages);
    }
}
