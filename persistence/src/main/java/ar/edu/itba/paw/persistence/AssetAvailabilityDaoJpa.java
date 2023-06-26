package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return em.createQuery("from LendingImpl as l where l.assetInstance = :ai and l.active != :active and l.active != :rejected", LendingImpl.class)
                .setParameter("ai", ai)
                .setParameter("active", LendingState.FINISHED)
                .setParameter("rejected", LendingState.REJECTED)
                .getResultList();
    }

    @Override
    public PagingImpl<LendingImpl> getPagingActiveLending(final AssetInstanceImpl ai, final int pageNum, final int itemsPerPage) {

        final Query queryNative = em.createNativeQuery("select l.id from lendings as l where l.assetinstanceid = :ai and l.active != :active and l.active != :rejected order by l.lenddate limit :limit offset :offset");

        final Query queryCount = em.createNativeQuery("select Count(l.id) from lendings as l where l.assetinstanceid = :ai and l.active != :active and l.active != :rejected");

        final int offset = (pageNum - 1) * itemsPerPage;

        queryCount.setParameter("ai", ai.getId());
        queryCount.setParameter("active", "FINISHED");
        queryCount.setParameter("rejected", "REJECTED");
        queryNative.setParameter("ai", ai.getId());
        queryNative.setParameter("limit", itemsPerPage);
        queryNative.setParameter("offset", offset);
        queryNative.setParameter("active", "FINISHED");
        queryNative.setParameter("rejected", "REJECTED");

        final int totalPages = (int) Math.ceil((double) ((Number) queryCount.getSingleResult()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (list.isEmpty())
            return new PagingImpl<>(new ArrayList<>(), pageNum, totalPages);
        final TypedQuery<LendingImpl> query = em.createQuery("FROM LendingImpl AS l WHERE id IN (:ids) ORDER BY l.lendDate", LendingImpl.class);
        query.setParameter("ids", list);
        List<LendingImpl> reviewList = query.getResultList();

        return new PagingImpl<>(reviewList, pageNum, totalPages);
    }

    @Override
    public void changeLendingStatus(LendingImpl lending, LendingState lendingState) {
        lending.setActive(lendingState);
        em.persist(lending);
    }

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

