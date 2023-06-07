package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserReviewsDaoJpa implements UserReviewsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addReview(final UserReview newReview) {
        em.persist(newReview);
    }

    @Override
    public double getRating(final UserImpl user) {
        try {
            String jql = "SELECT AVG(r.rating) FROM UserReview r WHERE r.recipient = :userId";
            return (Double) em.createQuery(jql)
                    .setParameter("userId", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0.0;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<UserReview> getUserReviewsByLendingIdAndUser(final int lendingId, final String user) {
        final Query query = em.createQuery("SELECT r FROM UserReview r WHERE r.lending.id = :lendingId AND r.reviewer.email = :user");
        query.setParameter("lendingId", (long) lendingId);
        query.setParameter("user", user);
        final List<UserReview> list = query.getResultList();
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagingImpl<UserReview> getUserReviewsAsBorrower(int pageNum, int itemsPerPage, final UserImpl recipient) {

        final Query queryNative = em.createNativeQuery("SELECT r.id FROM userreview as r JOIN lendings l on l.id = r.lendid WHERE r.recipient = :userRecipient AND  l.borrowerid = r.recipient ORDER BY r.id DESC LIMIT :limit OFFSET :offset");

        final Query queryCount = em.createNativeQuery("SELECT count(r.id) FROM userreview as r JOIN lendings l on l.id = r.lendid  WHERE r.recipient = :userRecipient AND l.borrowerid = r.recipient");

        final int offset = (pageNum - 1) * itemsPerPage;

        queryCount.setParameter("userRecipient",recipient.getId());
        queryNative.setParameter("limit",itemsPerPage);
        queryNative.setParameter("offset",offset);
        queryNative.setParameter("userRecipient",recipient.getId());

        final int totalPages = (int) Math.ceil((double) ((Number) queryCount.getSingleResult()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if(list.isEmpty())
            return new PagingImpl<>(new ArrayList<>(), pageNum, totalPages);
        return getReviewsFromIds(list,pageNum,totalPages);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage, final UserImpl reviewer) {

        final Query queryNative = em.createNativeQuery("SELECT r.id FROM userreview as r JOIN lendings l on l.id = r.lendid JOIN assetinstance a on a.id = l.assetinstanceid WHERE r.recipient = :userRecipient AND r.recipient = a.owner ORDER BY r.id DESC LIMIT :limit OFFSET :offset");

        final Query queryCount = em.createNativeQuery("SELECT count(r.id) FROM userreview as r JOIN lendings l on l.id = r.lendid JOIN assetinstance a on a.id = l.assetinstanceid WHERE r.recipient = :userRecipient AND r.recipient = a.owner");

        final int offset = (pageNum - 1) * itemsPerPage;

        queryCount.setParameter("userRecipient",reviewer.getId());
        queryNative.setParameter("limit",itemsPerPage);
        queryNative.setParameter("offset",offset);
        queryNative.setParameter("userRecipient",reviewer.getId());

        final int totalPages = (int) Math.ceil((double) ((Number) queryCount.getSingleResult()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if(list.isEmpty())
            return new PagingImpl<>(new ArrayList<>(), pageNum, totalPages);
        return getReviewsFromIds(list,pageNum,totalPages);
    }
    private PagingImpl<UserReview> getReviewsFromIds(final List<Long> list,final int pageNum,final int totalPages){
        final TypedQuery<UserReview> query = em.createQuery("FROM UserReview AS ai WHERE id IN (:ids) ORDER BY ai.id DESC" , UserReview.class);
        query.setParameter("ids", list);
        List<UserReview> reviewList = query.getResultList();
        return new PagingImpl<>(reviewList, pageNum, totalPages);
    }
}
