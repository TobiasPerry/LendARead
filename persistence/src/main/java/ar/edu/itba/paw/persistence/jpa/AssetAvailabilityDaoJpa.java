package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public void changeLendingStatus(LendingImpl lending, LendingState lendingState) {
        lending.setActive(lendingState);
        em.persist(lending);
    }

    //@Transactional
    @Override
    public Optional<List<LendingImpl>> getActiveLendingsStartingOn(LocalDate date) {
        TypedQuery<LendingImpl> lendingsQuery = em.createQuery("FROM LendingImpl l WHERE l.lendDate = :date AND l.active = 'ACTIVE'", LendingImpl.class);
        lendingsQuery.setParameter("date", date);
        List<LendingImpl> lendings = lendingsQuery.getResultList();
        return Optional.of(lendings);
    }

    @Override
    public Optional<List<LendingImpl>> getActiveLendingEndingOn(LocalDate date) {
        TypedQuery<LendingImpl> lendingsQuery = em.createQuery("FROM LendingImpl l WHERE l.devolutionDate = :date AND l.active = 'ACTIVE'", LendingImpl.class);
        lendingsQuery.setParameter("date", date);
        List<LendingImpl> lendings = lendingsQuery.getResultList();
        return Optional.of(lendings);
    }
}

