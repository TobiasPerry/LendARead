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
    public PagingImpl<LendingImpl> getPagingActiveLending( final int pageNum, final int itemsPerPage,final Integer aiId,final Integer borrowerId,final LendingState lendingState,final Integer lenderId) {

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

    @Override
    public Optional<LendingImpl> getLendingById(int lendingId) {
        return Optional.ofNullable(em.find(LendingImpl.class, lendingId));
    }
}

