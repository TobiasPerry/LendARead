package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.PageUserAssetsImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserAssetsDaoJpa implements UserAssetsDao {

    @PersistenceContext
    private EntityManager em;



    private String matchSortAttribuite(String sortAttribute) {
        switch (sortAttribute) {
            case "book_name":
                return "b.title";
            case "expected_retrieval_date":
                return "l.devolutiondate";
            case "borrower_name":
                return "u.name";
            case "author":
                return "b.author";
            case "language":
                return "b.lang";
            case "asset_state":
                return "ai.status";
            default:
                return "none";
        }
    }
    private String matchSortAttribuiteJpa(String sortAttribute) {
        switch (sortAttribute) {
            case "book_name":
                return "l.assetInstance.book.title";
            case "expected_retrieval_date":
                return "l.devolutionDate";
            case "borrower_name":
                return "l.userReference.name";
            case "author":
                return "l.assetInstance.book.author";
            case "language":
                return "l.assetInstance.book.languages";
            case "asset_state":
                return "l.assetInstance.assetState";
            default:
                return "none";
        }
    }
    private String matchSortAttribuiteUserAssetJpa(String sortAttribute) {
        switch (sortAttribute) {
            case "book_name":
                return "a.book.title";
            case "author":
                return "a.book.author";
            case "language":
                return "a.book.language";
            case "asset_state":
                return "a.assetState";
            default:
                return "none";
        }
    }

    @Override
    public PageUserAssets<LendingImpl> getLendedAssets(int pageNumber, int itemsPerPage, String email, String filterAtribuite, String filterValue, String sortAtribuite, String direction) {
        StringBuilder queryIds = new StringBuilder("SELECT  l.id FROM lendings l JOIN    assetinstance ai ON l.assetinstanceid = ai.id JOIN  book b ON ai.assetid = b.uid JOIN    users u ON l.borrowerid = u.id JOIN users owner ON ai.owner = owner.id WHERE owner.mail = :email ");
        StringBuilder queryCount = new StringBuilder("SELECT count(l.id) FROM lendings l JOIN    assetinstance ai ON l.assetinstanceid = ai.id JOIN    book b ON ai.assetid = b.uid JOIN    users u ON l.borrowerid = u.id JOIN users owner ON ai.owner = owner.id WHERE owner.mail = :email ");


        setQueryAttributes(filterAtribuite, sortAtribuite, direction, queryIds, queryCount);
        final int offset = (pageNumber - 1) * itemsPerPage;
        final int limit = itemsPerPage;
        String pagination = " LIMIT :limit OFFSET :offset ";
        queryIds.append(pagination);
        final Query queryNative = em.createNativeQuery(queryIds.toString());
        final Query queryCountNative = em.createNativeQuery(queryCount.toString());


        queryCountNative.setParameter("email", email);
        queryNative.setParameter("email", email);

        if(filterAtribuite.equalsIgnoreCase("status") || filterAtribuite.equalsIgnoreCase("lendingStatus")) {
            queryNative.setParameter("filterValue", filterValue.toUpperCase());
            queryCountNative.setParameter("filterValue", filterValue.toUpperCase());
        }
        queryNative.setParameter("limit", limit);
        queryNative.setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (list.isEmpty())
            return new PageUserAssetsImpl<>(new ArrayList<>(), 1, 1);


        String query = "SELECT l FROM LendingImpl l WHERE l.id in :ids ";

        if (!matchSortAttribuiteJpa(sortAtribuite).equalsIgnoreCase("none")) {
            query   +=" ORDER BY " + matchSortAttribuiteJpa(sortAtribuite);
            if (!direction.equalsIgnoreCase("none")) {
                query+= " "+ direction;
            }
        }

        List<LendingImpl> list2 =  em.createQuery(query, LendingImpl.class).setParameter("ids", list).getResultList();


        final int totalPages = (int) Math.ceil((double) ((Number) queryCountNative.getSingleResult()).longValue() / itemsPerPage);

        return new PageUserAssetsImpl<>(list2, pageNumber, totalPages);
    }

    @Override
    public PageUserAssets<LendingImpl> getBorrowedAssets(int pageNumber, int itemsPerPage, String email, String filterAtribuite, String filterValue, String sortAtribuite, String direction) {

        StringBuilder queryIds = new StringBuilder("SELECT l.id FROM    lendings l JOIN    assetinstance ai ON l.assetinstanceid = ai.id JOIN  book b ON ai.assetid = b.uid JOIN users u ON l.borrowerid = u.id JOIN  users owner ON ai.owner = owner.id WHERE  u.mail = :email ");
        StringBuilder queryCount = new StringBuilder("SELECT count(l.id) FROM    lendings l JOIN    assetinstance ai ON l.assetinstanceid = ai.id JOIN  book b ON ai.assetid = b.uid JOIN users u ON l.borrowerid = u.id JOIN  users owner ON ai.owner = owner.id WHERE  u.mail = :email ");

        setQueryAttributes(filterAtribuite, sortAtribuite, direction, queryIds, queryCount);

        final int offset = (pageNumber - 1) * itemsPerPage;
        final int limit = itemsPerPage;
        String pagination = " LIMIT :limit OFFSET :offset ";
        queryIds.append(pagination);

        final Query queryNative = em.createNativeQuery(queryIds.toString());
        final Query queryCountNative = em.createNativeQuery(queryCount.toString());

        queryCountNative.setParameter("email", email);
        queryNative.setParameter("email", email);

        if(filterAtribuite.equalsIgnoreCase("status") || filterAtribuite.equalsIgnoreCase("lendingStatus")) {
            queryNative.setParameter("filterValue", filterValue.toUpperCase());
            queryCountNative.setParameter("filterValue", filterValue.toUpperCase());
        }
        queryNative.setParameter("limit", limit);
        queryNative.setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (list.isEmpty())
            return new PageUserAssetsImpl<>(new ArrayList<>(), 1, 1);


        String query = "SELECT l FROM LendingImpl l WHERE l.id in :ids";

        if (!matchSortAttribuiteJpa(sortAtribuite).equalsIgnoreCase("none")) {
            query   +=" ORDER BY " + matchSortAttribuiteJpa(sortAtribuite);
            if (!direction.equalsIgnoreCase("none")) {
                query+= " "+ direction;
            }
        }
        List<LendingImpl> list2 =  em.createQuery(query, LendingImpl.class).setParameter("ids", list).getResultList();



        final int totalPages = (int) Math.ceil((double) ((Number) queryCountNative.getSingleResult()).longValue() / itemsPerPage);

        return new PageUserAssetsImpl<>(list2, pageNumber, totalPages);
    }

    private void setQueryAttributes(String filterAtribuite, String sortAtribuite, String direction, StringBuilder queryIds, StringBuilder queryCount) {
        if (filterAtribuite.equalsIgnoreCase("status")) {
            queryIds.append( "AND (l.active = 'DELIVERED' OR l.active = 'ACTIVE') AND ai.status = :filterValue");
            queryCount.append("AND (l.active = 'DELIVERED' OR l.active = 'ACTIVE') AND ai.status = :filterValue");
        }
        if (filterAtribuite.equalsIgnoreCase("lendingStatus")) {
            queryIds.append("AND l.active = :filterValue");
            queryCount.append("AND l.active = :filterValue");
        }
        if (filterAtribuite.equalsIgnoreCase("delayed")) {
            queryIds.append("AND l.active = 'DELIVERED' AND date(l.devolutiondate) < CURRENT_DATE");
            queryCount.append("AND l.active = 'DELIVERED' AND date(l.devolutiondate) < CURRENT_DATE");
        }
        if (!matchSortAttribuite(sortAtribuite).equalsIgnoreCase("none")) {
            queryIds.append(" ORDER BY ").append(matchSortAttribuite(sortAtribuite));
            if (!direction.equalsIgnoreCase("none")) {
                queryIds.append(" ").append(direction);
            }
        }
    }

    @Override
    public PageUserAssets<AssetInstanceImpl> getUsersAssets(int pageNumber, int itemsPerPage, String email, String filterAtribuite, String filterValue, String sortAtribuite, String direction) {
        StringBuilder queryIds = new StringBuilder("SELECT ai.id FROM assetinstance ai join users u on ai.owner = u.id JOIN book b ON ai.assetid = b.uid WHERE u.mail = :email AND ai.status IN ('PUBLIC', 'PRIVATE')");

        StringBuilder queryCount = new StringBuilder("SELECT count(ai.id) FROM assetinstance ai join users u on ai.owner = u.id WHERE u.mail = :email AND ai.status IN ('PUBLIC', 'PRIVATE')");

        if (!filterAtribuite.equalsIgnoreCase("none")) {
            queryIds.append(" AND ").append(filterAtribuite).append(" = :filterValue");
            queryCount.append(" AND ").append(filterAtribuite).append(" = :filterValue");
        }
        if (!matchSortAttribuite(sortAtribuite).equalsIgnoreCase("none")) {
            queryIds.append(" ORDER BY ").append(matchSortAttribuite(sortAtribuite));
            if (!direction.equalsIgnoreCase("none")) {
                queryIds.append(" ").append(direction);
            }
        }

        queryIds.append(" LIMIT :limit OFFSET :offset");
        final Query queryNative = em.createNativeQuery(queryIds.toString());
        final Query queryCountNative = em.createNativeQuery(queryCount.toString());
        queryNative.setParameter("email", email);
        queryCountNative.setParameter("email", email);


        final int offset = (pageNumber - 1) * itemsPerPage;
        final int limit = itemsPerPage;

        queryNative.setParameter("limit", limit);
        queryNative.setParameter("offset", offset);

        if (!filterAtribuite.equalsIgnoreCase("none")) {
            queryNative.setParameter("filterValue", filterValue.toUpperCase());
            queryCountNative.setParameter("filterValue", filterValue.toUpperCase());
        }

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        if (list.isEmpty())
            return new PageUserAssetsImpl<>(new ArrayList<>(), 1, 1);

        String query = "SELECT a FROM AssetInstanceImpl a WHERE a.id in :ids ";

        if (!matchSortAttribuiteUserAssetJpa(sortAtribuite).equalsIgnoreCase("none")) {
            query   +=" ORDER BY " + matchSortAttribuiteUserAssetJpa(sortAtribuite);
            if (!direction.equalsIgnoreCase("none")) {
                query+= " "+ direction;
            }
        }

        List<AssetInstanceImpl> assetInstanceList=  em.createQuery(query, AssetInstanceImpl.class).setParameter("ids", list).getResultList();
        final int totalPages = (int) Math.ceil((double) ((Number) queryCountNative.getSingleResult()).longValue() / itemsPerPage);

        return new PageUserAssetsImpl<>(assetInstanceList, pageNumber, totalPages);
    }

    @Override
    public Optional<LendingImpl> getBorrowedAsset(int lendingId) {
        String query = "SELECT l FROM LendingImpl l WHERE l.id = :lendingId";
        List<LendingImpl> list =  em.createQuery(query, LendingImpl.class).setParameter("lendingId", new Long(lendingId)).getResultList();
        return list.stream().findFirst();
    }
}
