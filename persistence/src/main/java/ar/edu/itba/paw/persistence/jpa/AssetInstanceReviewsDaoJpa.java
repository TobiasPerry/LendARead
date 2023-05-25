package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
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
    public double getRating(AssetInstance assetInstance) {
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
    @SuppressWarnings("unchecked")
    public List<AssetInstanceReview> getAssetInstanceReviews(AssetInstance assetInstance) {
        String hql = "SELECT r FROM AssetInstanceReview r WHERE r.assetInstanceId = :assetInstance";
        return (List<AssetInstanceReview>) em.createQuery(hql).setParameter("assetInstance", assetInstance.getId()).getResultList();
    }
}
