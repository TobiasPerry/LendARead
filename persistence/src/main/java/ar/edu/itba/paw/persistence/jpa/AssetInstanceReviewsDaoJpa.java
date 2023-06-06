package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceReviewsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    public double getRating(AssetInstanceImpl assetInstance) {
        try {
            String hql = "SELECT AVG(r.rating) FROM AssetInstanceReview as r WHERE r.assetInstance.id = :assetInstance";
            return (Double) em.createQuery(hql)
                    .setParameter("assetInstance", assetInstance.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0.0;
        }
    }



    @Override
    @SuppressWarnings("unchecked")
    public PagingImpl<AssetInstanceReview> getAssetInstanceReviews(int pageNum, int itemsPerPage, AssetInstanceImpl assetInstance) {

        final Query queryNative = em.createNativeQuery("SELECT r.id FROM AssetInstanceReview as r JOIN lendings l on r.lendid = l.id JOIN assetinstance a on a.id = l.assetinstanceid WHERE a.id = :assetInstance ORDER BY r.id DESC LIMIT :limit OFFSET :offset");

        final Query queryCount = em.createNativeQuery("SELECT count(r.id) FROM AssetInstanceReview as r JOIN lendings l on r.lendid = l.id JOIN assetinstance a on a.id = l.assetinstanceid WHERE a.id = :assetInstance");

        final int offset = (pageNum - 1) * itemsPerPage;

        queryCount.setParameter("assetInstance",assetInstance.getId());
        queryNative.setParameter("limit",itemsPerPage);
        queryNative.setParameter("offset",offset);
        queryNative.setParameter("assetInstance",assetInstance.getId());

        final int totalPages = (int) Math.ceil((double) ((Number) queryCount.getSingleResult()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if(list.isEmpty())
            return new PagingImpl<>(new ArrayList<>(), pageNum, totalPages);
        final TypedQuery<AssetInstanceReview> query = em.createQuery("FROM AssetInstanceReview AS ai WHERE id IN (:ids) ORDER BY ai.id DESC" , AssetInstanceReview.class);
        query.setParameter("ids", list);
        List<AssetInstanceReview> reviewList = query.getResultList();

        return new PagingImpl<>(reviewList, pageNum, totalPages);
    }

}
