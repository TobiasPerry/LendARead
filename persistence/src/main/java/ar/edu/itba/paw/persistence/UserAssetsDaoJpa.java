package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;
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
        List<Lending> list = em.createQuery(query, Lending.class).setParameter("lendingId", new Long(lendingId)).getResultList();
        return list.stream().findFirst();
    }
}
