package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceReviewsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

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
            String hql = "SELECT AVG(r.rating) FROM AssetInstanceReview r WHERE r.assetInstanceId = :assetInstance";
            return (Double) em.createQuery(hql)
                    .setParameter("assetInstance", assetInstance.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0.0;
        }
    }

    @Override
    public List<AssetInstanceReview> getAssetInstanceReviews(AssetInstanceImpl assetInstance) {
        return null;
    }

   /* @Override
    @SuppressWarnings("unchecked")
    public PagingImpl<AssetInstanceReview> getAssetInstanceReviews(int pageNum, int itemsPerPage, AssetInstanceImpl assetInstance) {

        final Query queryNative = em.createNativeQuery("SELECT id FROM AssetInstanceReview  WHERE r.assetInstanceId = :assetInstance");

        final Query queryCount = em.createNativeQuery("SELECT count(id) FROM AssetInstanceReview  WHERE r.assetInstanceId = :assetInstance");


        String hql = "SELECT r FROM AssetInstanceReview r WHERE r.assetInstanceId = :assetInstance";
        return (List<AssetInstanceReview>) em.createQuery(hql).setParameter("assetInstance", assetInstance.getId()).getResultList();
    }*/
}
