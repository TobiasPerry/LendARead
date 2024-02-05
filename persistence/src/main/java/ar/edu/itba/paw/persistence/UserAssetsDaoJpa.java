package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAssetsDaoJpa implements UserAssetsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Lending> getBorrowedAsset(int lendingId) {
        String query = "SELECT l FROM Lending l WHERE l.id = :lendingId";
        List<Lending> list = em.createQuery(query, Lending.class).setParameter("lendingId", (long) lendingId).getResultList();
        return list.stream().findFirst();
    }

    @Override
    public Integer getActiveLendingsCount(final long assetInstanceId) {
        String query = "SELECT COUNT(l) FROM Lending l WHERE l.assetInstance.id = :assetInstanceId AND l.active = 'DELIVERED'";
        return ((Long) em.createQuery(query).setParameter("assetInstanceId",  assetInstanceId).getSingleResult()).intValue();
    }

    @Override
    public Integer getNonFinishedLendingsCount(final long assetInstanceId) {
        String query = "SELECT COUNT(l) FROM Lending l WHERE l.assetInstance.id = :assetInstanceId AND l.active != 'FINISHED'";
        return ((Long) em.createQuery(query).setParameter("assetInstanceId",  assetInstanceId).getSingleResult()).intValue();
    }
}
