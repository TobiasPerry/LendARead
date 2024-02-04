package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.assetLendingContext.LendingState;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collections;
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
        return em.createQuery("from Lending as l where l.assetInstance = :ai and l.active != :active and l.active != :rejected and l.active != :cancel", Lending.class)
                .setParameter("ai", ai)
                .setParameter("active", LendingState.FINISHED)
                .setParameter("rejected", LendingState.REJECTED)
                .setParameter("cancel", LendingState.CANCELED)
                .getResultList();
    }

    @Override
    public PagingImpl<Lending> getPagingActiveLending(final int pageNum, final int itemsPerPage, final Integer aiId, final Integer borrowerId, final List<String> lendingState, final Integer lenderId, final String sort, final String sortDirection, final LocalDate startingBefore, final LocalDate startingAfter, final LocalDate endBefore, final LocalDate endAfter) {

        final StringBuilder queryNativeStringBuilder = new StringBuilder("select l.id from lendings as l join assetInstance as a on l.assetinstanceid = a.id join book as b on a.assetid = b.uid join users as borrower on l.borrowerid = borrower.id join users as owner on a.owner = owner.id ");

        boolean first = true;
        if (lenderId != null){
            queryNativeStringBuilder.append("WHERE a.owner = :lenderId ");
            first = false;
        }
        if (lendingState != null && !lendingState.isEmpty()){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.active in (:active) ");
            first = false;
        }
        if(aiId != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.assetinstanceid = :ai ");
            first = false;

        }
        if(startingBefore != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.lenddate <= :startingBefore ");
            first = false;
        }
        if(startingAfter != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.lenddate >= :startingAfter ");
            first = false;
        }
        if(borrowerId != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.borrowerid = :borrowerId ");
            first = false;
        }
        if(endBefore != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.devolutiondate <= :endBefore ");
            first = false;
        }
        if(endAfter != null){
            queryNativeStringBuilder.append(first ? "WHERE " : "AND ");
            queryNativeStringBuilder.append(" l.devolutiondate >= :endAfter ");
            first = false;
        }

        queryNativeStringBuilder.append("group by l.id,l.lenddate,l.devolutiondate,owner.name,borrower.name,b.title,l.active,a.physicalcondition").append(getSortQueryForNativeQuery(sort, sortDirection));
        final Query queryCount = em.createNativeQuery(queryNativeStringBuilder.toString());
        queryNativeStringBuilder.append(" limit :limit offset :offset");
        final Query queryNative = em.createNativeQuery(queryNativeStringBuilder.toString());


        final int offset = (pageNum - 1) * itemsPerPage;
        if (lenderId != null){
            queryNative.setParameter("lenderId", lenderId);
            queryCount.setParameter("lenderId", lenderId);
        }
        if (lendingState != null && !lendingState.isEmpty()){
            List<String> states = lendingState.stream().map(String::toUpperCase).collect(Collectors.toList());
            queryNative.setParameter("active", states);
            queryCount.setParameter("active", states);

        }
        if(startingAfter != null){
            queryNative.setParameter("startingAfter", startingAfter);
            queryCount.setParameter("startingAfter", startingAfter);

        }
        if(startingBefore != null){
            queryNative.setParameter("startingBefore", startingBefore);
            queryCount.setParameter("startingBefore", startingBefore);

        }
        if(aiId != null){
            queryNative.setParameter("ai", aiId);
            queryCount.setParameter("ai", aiId);

        }
        if(borrowerId != null){
            queryNative.setParameter("borrowerId", borrowerId);
            queryCount.setParameter("borrowerId", borrowerId);

        }
        if(endAfter != null){
            queryNative.setParameter("endAfter", endAfter);
            queryCount.setParameter("endAfter", endAfter);

        }
        if(endBefore != null){
            queryNative.setParameter("endBefore", endBefore);
            queryCount.setParameter("endBefore", endBefore);

        }

        queryNative.setParameter("limit", itemsPerPage);
        queryNative.setParameter("offset", offset);


        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryCount.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        final int totalPages = (int) Math.ceil((double) (list.size()) / itemsPerPage);
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (ids.isEmpty())
            return new PagingImpl<>(Collections.emptyList(), pageNum, totalPages);

        final TypedQuery<Lending> query = em.createQuery("FROM Lending AS l WHERE id IN (:ids) " + getSortQueryForTypedQuery(sort,sortDirection), Lending.class);
        query.setParameter("ids", ids);
        List<Lending> reviewList = query.getResultList();

        return new PagingImpl<>(reviewList, pageNum, totalPages);
    }
    private String getSortQueryForTypedQuery(String sort, String sortDirection) {
        if (sort == null || sort.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        switch (sort) {
            case "LENDDATE":
                sb.append( " ORDER BY l.lendDate ");
                break;
            case "DEVOLUTIONDATE":
                sb.append( " ORDER BY l.devolutionDate ");
                break;
            case "BORROWER_USER":
                sb.append( " ORDER BY l.userReference.name " );
                break;
            case "LENDER_USER":
                sb.append( " ORDER BY l.assetInstance.userReference.name " );
                break;
            case "TITLE":
                sb.append( " ORDER BY l.assetInstance.book.title " );
                break;
            case "PHYSICAL_CONDITION":
                sb.append( " ORDER BY l.assetInstance.physicalCondition " );
                break;
            case "LENDING_STATUS":
                sb.append( " ORDER BY l.active " );
                break;
            default:
                return "";
        }
        sb.append(sortDirection != null ? SortDirection.fromString(sortDirection).getValue() : "ASC");
        return sb.toString();
    }
    private String getSortQueryForNativeQuery(String sort, String sortDirection) {
        if (sort == null || sort.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        switch (sort) {
            case "LENDDATE":
                sb.append( " ORDER BY l.lenddate ");
                break;
            case "DEVOLUTIONDATE":
                sb.append( " ORDER BY l.devolutionDate ");
                break;
            case "BORROWER_USER":
                sb.append( " ORDER BY borrower.name " );
                break;
            case "LENDER_USER":
                sb.append( " ORDER BY owner.name " );
                break;
            case "TITLE":
                sb.append( " ORDER BY b.title " );
                break;
            case "LENDING_STATUS":
                sb.append( " ORDER BY l.active " );
                break;
            case "PHYSICAL_CONDITION":
                sb.append( " ORDER BY a.physicalcondition " );
                break;
            default:
                return "";
        }
        sb.append(sortDirection != null ? SortDirection.fromString(sortDirection).getValue() : "ASC");
        return sb.toString();


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
        return Optional.ofNullable(em.find(Lending.class, (long)lendingId));
    }
}

