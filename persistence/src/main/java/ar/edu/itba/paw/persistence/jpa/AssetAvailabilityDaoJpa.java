package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AssetAvailabilityDaoJpa implements AssetAvailabilityDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public LendingImpl borrowAssetInstance(AssetInstanceImpl assetInstance, UserImpl user, LocalDate borrowDate, LocalDate devolutionDate, LendingState lendingState) {
        LendingImpl lending = new LendingImpl(assetInstance, user, borrowDate, devolutionDate, lendingState);
        em.persist(lending);
        return lending;
    }

    @Override
    public List<LendingImpl> getActiveLendings(AssetInstanceImpl ai) {
        return em.createQuery("from LendingImpl as l where l.assetInstance = :ai and l.active != :active", LendingImpl.class)
                .setParameter("ai", ai)
                .setParameter("active", LendingState.FINISHED)
                .getResultList();
    }

    @Override
    public void changeLendingStatus(LendingImpl lending, LendingState lendingState) {
        lending.setActive(lendingState);
        em.persist(lending);
    }
}

