package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstanceReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceReviewsDao;
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
public class AssetInstanceReviewsDaoJpa implements AssetInstanceReviewsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addReview(AssetInstanceReview assetInstanceReview) {
        em.persist(assetInstanceReview);
    }




    @Override
    @SuppressWarnings("unchecked")
    public PagingImpl<AssetInstanceReview> getAssetInstanceReviews(int pageNum, int itemsPerPage, AssetInstance assetInstance) {

        final Query queryNative = em.createNativeQuery("SELECT r.id FROM AssetInstanceReview as r JOIN lendings l on r.lendid = l.id JOIN assetinstance a on a.id = l.assetinstanceid WHERE a.id = :assetInstance ORDER BY r.id DESC LIMIT :limit OFFSET :offset");

        final Query queryCount = em.createNativeQuery("SELECT count(r.id) FROM AssetInstanceReview as r JOIN lendings l on r.lendid = l.id JOIN assetinstance a on a.id = l.assetinstanceid WHERE a.id = :assetInstance");

        final int offset = (pageNum - 1) * itemsPerPage;

        queryCount.setParameter("assetInstance", assetInstance.getId());
        queryNative.setParameter("limit", itemsPerPage);
        queryNative.setParameter("offset", offset);
        queryNative.setParameter("assetInstance", assetInstance.getId());

        final int totalPages = (int) Math.ceil((double) ((Number) queryCount.getSingleResult()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (list.isEmpty())
            return new PagingImpl<>(Collections.emptyList(), pageNum, totalPages);
        final TypedQuery<AssetInstanceReview> query = em.createQuery("FROM AssetInstanceReview AS ai WHERE id IN (:ids) ORDER BY ai.id DESC", AssetInstanceReview.class);
        query.setParameter("ids", list);
        List<AssetInstanceReview> reviewList = query.getResultList();

        return new PagingImpl<>(reviewList, pageNum, totalPages);
    }

    @Override
    public Optional<AssetInstanceReview> getReviewById(int reviewId) {
        final  TypedQuery<AssetInstanceReview>  query = em.createQuery("SELECT r FROM AssetInstanceReview r WHERE r.id= :reviewId",AssetInstanceReview.class);
        query.setParameter("reviewId", (long) reviewId);
        List<AssetInstanceReview> assetInstanceReviews = query.getResultList();
        return assetInstanceReviews.stream().findFirst();
    }

    @Override
    public void deleteReview(AssetInstanceReview assetInstanceReview) {
        em.remove(assetInstanceReview);
    }

    @Override
    public Optional<AssetInstanceReview> getReviewByLendingId(int lendingId) {
        final  TypedQuery<AssetInstanceReview>  query = em.createQuery("SELECT r FROM AssetInstanceReview r WHERE r.lending.id= :lendingId",AssetInstanceReview.class);
        query.setParameter("lendingId", (long) lendingId);
        List<AssetInstanceReview> assetInstanceReviews = query.getResultList();
        return assetInstanceReviews.stream().findFirst();
    }


}
