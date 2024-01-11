package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.AssetInstanceSort;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AssetInstanceDaoJpa implements AssetInstanceDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public AssetInstance addAssetInstance(AssetInstance ai) {
        em.persist(ai);
        return ai;
    }

    @Override
    public Optional<AssetInstance> getAssetInstance(int assetId) {
        String queryString = "FROM AssetInstance as ai WHERE ai.id = :id";
        TypedQuery<AssetInstance> query = em.createQuery(queryString, AssetInstance.class);
        query.setParameter("id", (long) assetId);
        List<AssetInstance> list = query.getResultList();
        return list.stream().findFirst();
    }

    @Override
    public void changeStatus(AssetInstance assetInstance, AssetState as) {
        assetInstance.setAssetState(as);
        em.persist(assetInstance);
    }



    @Override
    public AbstractPage<AssetInstance> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery) {

        // Base query for getting the assets IDs for a given page
        StringBuilder queryNativeString = new StringBuilder("SELECT ai.id FROM AssetInstance ai " +
                "LEFT OUTER JOIN (SELECT lendings.assetinstanceid AS assetinstanceid, AVG(rating) AS avg_rating FROM assetInstancereview JOIN lendings ON assetInstancereview.lendid = lendings.id GROUP BY lendings.assetinstanceid) AS avg_reviews ON ai.id = avg_reviews.assetInstanceid " +
                "JOIN Book b on ai.assetid = b.uid " +
                "WHERE ai.status = :state ");

        // Query filters based on the search filters
        StringBuilder queryFilters = new StringBuilder();

        // Order by (Postgres and ORM)
        String orderByPostgres = " ORDER BY " +
                getPostgresFromSort(searchQuery.getSort()) +
                " " +
                getPostgresFromSortDirection(searchQuery.getSortDirection()) + " ";
        String orderByORM = " ORDER BY " +
                getOrmFromSort(searchQuery.getSort()) +
                " " +
                getOrmFromSortDirection(searchQuery.getSortDirection()) + " ";

        // Pagination
        final int offset = (pageNum - 1) * itemsPerPage;
        final int limit = itemsPerPage;
        String pagination = " LIMIT :limit OFFSET :offset ";

        // If there's a search parameter
        if (!searchQuery.getSearch().equals("")) {
            queryFilters.append(" AND ( b.title ILIKE CONCAT('%', :search, '%') OR b.author ILIKE CONCAT('%', :search, '%') ) ");
        }

        // If the search is filtered bt languages
        if (!searchQuery.getLanguages().isEmpty()) {
            queryFilters.append(" AND b.lang IN (:languages) ");
        }

        // If the search is filtered by physicalConditions
        if (!searchQuery.getPhysicalConditions().isEmpty()) {
            queryFilters.append(" AND ai.physicalCondition IN (:physicalConditions) ");
        }
        if (searchQuery.getUserId() != -1) {
            queryFilters.append(" AND ai.owner = :userId ");
        }

        // If there's a rating filter parameter
        queryFilters.append(" AND COALESCE(avg_reviews.avg_rating ,3) >= :min_rating AND COALESCE(avg_reviews.avg_rating ,3) <= :max_rating ");

        // Append the filters
        queryNativeString.append(queryFilters);

        // Order by and pagination for the native queries
        queryNativeString.append(orderByPostgres);

        // Count pages
        final Query queryCount = em.createNativeQuery(queryNativeString.toString());

        // Add pagination
        queryNativeString.append(pagination);

        // Get IDs for given page
        final Query queryNative = em.createNativeQuery(queryNativeString.toString());

        final String search = searchQuery.getSearch().toUpperCase().replace("%", "\\%");
        if (!searchQuery.getSearch().equals("")) {
            queryNative.setParameter("search", search);
            queryCount.setParameter("search", search);
        }

        // If the search is filtered bt languages
        if (!searchQuery.getLanguages().isEmpty()) {
            queryNative.setParameter("languages", searchQuery.getLanguages());
            queryCount.setParameter("languages", searchQuery.getLanguages());
        }

        // If the search is filtered by physicalConditions
        if (!searchQuery.getPhysicalConditions().isEmpty()) {
            queryNative.setParameter("physicalConditions", searchQuery.getPhysicalConditions());
            queryCount.setParameter("physicalConditions", searchQuery.getPhysicalConditions());
        }
        if (searchQuery.getUserId() != -1) {
            queryNative.setParameter("userId", searchQuery.getUserId());
            queryCount.setParameter("userId", searchQuery.getUserId());
        }
        queryNative.setParameter("min_rating", searchQuery.getMinRating());
        queryNative.setParameter("max_rating", searchQuery.getMaxRating());

        queryCount.setParameter("min_rating", searchQuery.getMinRating());
        queryCount.setParameter("max_rating", searchQuery.getMaxRating());

        queryNative.setParameter("state",searchQuery.getAssetState().toString() );
        queryCount.setParameter("state",searchQuery.getAssetState().toString()  );

        queryNative.setParameter("limit", limit);
        queryNative.setParameter("offset", offset);

        // Get the list of IDs (not paginated) of assetInstances and calculate the number of pages
        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) queryCount.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        final int totalPages = (int) Math.ceil((double) ((Number) ids.size()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());

        // In case of empty result -> Return a Page with empty lists
        if (list.isEmpty())
            return new PagingImpl<>(new ArrayList<>(), pageNum, totalPages);

        // Get the AssetInstances that match those IDs for given page
        final TypedQuery<AssetInstance> query = em.createQuery("FROM AssetInstance AS ai WHERE id IN (:ids) " + orderByORM, AssetInstance.class);
        query.setParameter("ids", list);
        List<AssetInstance> assetInstances = query.getResultList();
        return new PagingImpl<>(assetInstances, pageNum, totalPages);
    }

    private List<String> getLanguages(List<Long> ids){
        String queryString = "SELECT DISTINCT ai.book.language.code FROM AssetInstance AS ai WHERE ai.assetState = :state AND ai.id IN (:ids)";

        TypedQuery<String> query = em.createQuery(queryString, String.class);
        query.setParameter("ids", ids);
        query.setParameter("state", AssetState.PUBLIC);

        return query.getResultList();

    }

    private List<String> getPhysicalConditions(List<Long> ids){
        String queryString = "SELECT DISTINCT ai.physicalCondition FROM AssetInstance AS ai WHERE ai.assetState = :state AND ai.id IN (:ids)";

        TypedQuery<PhysicalCondition> query = em.createQuery(queryString, PhysicalCondition.class);
        query.setParameter("ids", ids);
        query.setParameter("state", AssetState.PUBLIC);

        return query.getResultList().stream().map(Enum::name).collect(Collectors.toList());

    }

    private List<PhysicalCondition> getPhysicalConditionsList(List<String> list) {
        return list.stream().map(PhysicalCondition::fromString).collect(Collectors.toList());
    }

    private String getPostgresFromSort(AssetInstanceSort assetInstanceSort) {
        if (assetInstanceSort == null)
            return "ai.id";
        switch (assetInstanceSort) {
            case TITLE_NAME:
                return "b.title";
            case AUTHOR_NAME:
                return "b.author";
            case RECENT:
                return "ai.id";
            case LANGUAGE:
                return "b.lang";
        }
        return "ai.id";
    }

    private String getOrmFromSort(AssetInstanceSort assetInstanceSort) {
        if (assetInstanceSort == null)
            return "ai.id";
        switch (assetInstanceSort) {
            case TITLE_NAME:
                return "ai.book.title";
            case AUTHOR_NAME:
                return "ai.book.author";
            case RECENT:
                return "ai.id";
        }
        return "ai.id";
    }

    private String getPostgresFromSortDirection(SortDirection sortDirection) {
        if (sortDirection == null)
            return "ASC";
        switch (sortDirection) {
            case ASCENDING:
                return "ASC";
            case DESCENDING:
                return "DESC";
        }
        return "ASC";
    }

    private String getOrmFromSortDirection(SortDirection sortDirection) {
        return getPostgresFromSortDirection(sortDirection);
    }

}
