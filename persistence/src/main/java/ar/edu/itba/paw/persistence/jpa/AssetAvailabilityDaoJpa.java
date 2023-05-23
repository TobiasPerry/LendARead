package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.Lending;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Repository
public class AssetAvailabilityDaoJpa implements AssetAvailabilityDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int borrowAssetInstance(int assetInstanceId, int userId, LocalDate borrowDate, LocalDate devolutionDate, LendingState lendingState) {
        LendingImpl lending = new LendingImpl(assetInstanceId, userId, borrowDate, devolutionDate, lendingState);
        em.persist(lending);
        return Math.toIntExact(lending.getId());
    }

    @Override
    public boolean changeLendingStatus(int lendingId, LendingState lendingState) {
        Lending lending = em.find(Lending.class, lendingId);
        if (lending == null) return false;

        lending.setActive(lendingState);
        em.persist(lending);
        return true;
    }
}

