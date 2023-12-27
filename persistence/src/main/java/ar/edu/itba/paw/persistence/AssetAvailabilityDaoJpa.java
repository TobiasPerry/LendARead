package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.User;
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
    public Lending borrowAssetInstance(AssetInstance assetInstance, User user, LocalDate borrowDate, LocalDate devolutionDate, LendingState lendingState) {
        Lending lending = new Lending(assetInstance, user, borrowDate, devolutionDate, lendingState);
        em.persist(lending);
        return lending;
    }

    @Override
    public List<Lending> getActiveLendings(AssetInstance ai) {
        return em.createQuery("from Lending as l where l.assetInstance = :ai and l.active != :active and l.active != :rejected", Lending.class)
                .setParameter("ai", ai)
                .setParameter("active", LendingState.FINISHED)
                .setParameter("rejected", LendingState.REJECTED)
                .getResultList();
    }

    @Override
    public PagingImpl<Lending> getPagingActiveLending(final int pageNum, final int itemsPerPage, final Integer aiId, final Integer borrowerId, final LendingState lendingState, final Integer lenderId) {

        final StringBuilder queryNativeStringBuilder = new StringBuilder("select l.id from lendings as l join assetInstance as a on l.assetinstanceid = a.id ");

        boolean first = true;
        if (lenderId != null){
            queryNativeStringBuilder.append("WHERE a.owner = :lenderId ");
            first = false;
        }
        if (lendingState != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.active = :active ");
            first = false;
        }
        if(aiId != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.assetinstanceid = :ai");
            first = false;

        }
        if(borrowerId != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.borrowerid = :borrowerId ");
            first = false;
        }
        queryNativeStringBuilder.append("group by l.id,l.lenddate order by l.lenddate limit :limit offset :offset");

        final Query queryNative = em.createNativeQuery(queryNativeStringBuilder.toString());


        final int offset = (pageNum - 1) * itemsPerPage;
        if (lenderId != null){
            queryNative.setParameter("lenderId", lenderId);
        }
        if (lendingState != null){
            queryNative.setParameter("active", lendingState.toString());
        }
        if(aiId != null){
            queryNative.setParameter("ai", aiId);
        }
        if(borrowerId != null){
            queryNative.setParameter("borrowerId", borrowerId);
        }


        queryNative.setParameter("limit", itemsPerPage);
        queryNative.setParameter("offset", offset);


        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        final int totalPages = (int) Math.ceil((double) (list.size()) / itemsPerPage);

        if (list.isEmpty())
            return new PagingImpl<>(new ArrayList<>(), pageNum, 1);

        final TypedQuery<Lending> query = em.createQuery("FROM Lending AS l WHERE id IN (:ids) ORDER BY l.lendDate", Lending.class);
        query.setParameter("ids", list);
        List<Lending> reviewList = query.getResultList();

        return new PagingImpl<>(reviewList, pageNum, totalPages);
    }

    @Override
    public void changeLendingStatus(Lending lending, LendingState lendingState) {
        lending.setActive(lendingState);
        em.persist(lending);
    }

    @Override
    public Optional<List<Lending>> getActiveLendingsStartingOn(LocalDate date) {
        TypedQuery<Lending> lendingsQuery = em.createQuery("FROM Lending l WHERE l.lendDate = :date AND l.active = 'ACTIVE'", Lending.class);
        lendingsQuery.setParameter("date", date);
        List<Lending> lendings = lendingsQuery.getResultList();
        return Optional.of(lendings);
    }

    @Override
    public Optional<List<Lending>> getActiveLendingEndingOn(LocalDate date) {
        TypedQuery<Lending> lendingsQuery = em.createQuery("FROM Lending l WHERE l.devolutionDate = :date AND l.active = 'ACTIVE'", Lending.class);
        lendingsQuery.setParameter("date", date);
        List<Lending> lendings = lendingsQuery.getResultList();
        return Optional.of(lendings);
    }

    @Override
    public Optional<Lending> getLendingById(int lendingId) {
        return Optional.ofNullable(em.find(Lending.class, lendingId));
    }
}

