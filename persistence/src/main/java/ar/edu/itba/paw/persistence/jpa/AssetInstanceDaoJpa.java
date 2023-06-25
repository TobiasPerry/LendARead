package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.Sort;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
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
    public AssetInstanceImpl addAssetInstance(AssetInstanceImpl ai) {
        em.persist(ai);
        return ai;
    }

    @Override
    public void changePhysicalCondition(final AssetInstanceImpl ai, final PhysicalCondition physicalCondition){
        ai.setPhysicalCondition(physicalCondition);
        em.persist(ai);
    }
    @Override
    public void changeLocation(final AssetInstanceImpl ai,final LocationImpl location){
        ai.setLocation(location);
        em.persist(ai);
    }
    @Override
    public void changeImage(final AssetInstanceImpl ai,final ImageImpl image){
        ai.setImage(image);
        em.persist(ai);
    }
    @Override
    public void changeMaxLendingDays(final AssetInstanceImpl ai, final int maxLendingDays){

        ai.setMaxLendingDays(maxLendingDays);
        em.persist(ai);
    }
    @Override
    public Optional<AssetInstanceImpl> getAssetInstance(int assetId) {
        String queryString = "FROM AssetInstanceImpl as ai WHERE ai.id = :id";
        TypedQuery<AssetInstanceImpl> query = em.createQuery(queryString, AssetInstanceImpl.class);
        query.setParameter("id", (long) assetId);
        List<AssetInstanceImpl> list = query.getResultList();
        return list.stream().findFirst();
    }

    @Override
    public void changeStatus(AssetInstanceImpl assetInstance, AssetState as) {
        assetInstance.setAssetState(as);
        em.persist(assetInstance);
    }

    @Override
    public void setReservability(AssetInstanceImpl ai, boolean value) {
        ai.setReservable(value);
        em.persist(ai);
    }

    @Override
    public void changeStatusByLendingId(AssetInstanceImpl ai, AssetState as) {
        ai.setAssetState(as);
        em.persist(ai);
    }

    @Override
    public Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery) {

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
        if(!searchQuery.getSearch().equals("")) {
            queryFilters.append(" AND ( b.title ILIKE CONCAT('%', :search, '%') OR b.author ILIKE CONCAT('%', :search, '%') ) ");
        }

        // If the search is filtered bt languages
        if(!searchQuery.getLanguages().isEmpty()){
            queryFilters.append(" AND b.lang IN (:languages) ");
        }

        // If the search is filtered by physicalConditions
        if(!searchQuery.getPhysicalConditions().isEmpty()){
            queryFilters.append(" AND ai.physicalCondition IN (:physicalConditions) ");
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
        if(!searchQuery.getSearch().equals("")) {
            queryNative.setParameter("search", search);
            queryCount.setParameter("search", search);
        }

        // If the search is filtered bt languages
        if(!searchQuery.getLanguages().isEmpty()){
            queryNative.setParameter("languages", searchQuery.getLanguages());
            queryCount.setParameter("languages", searchQuery.getLanguages());
        }

        // If the search is filtered by physicalConditions
        if(!searchQuery.getPhysicalConditions().isEmpty()){
            queryNative.setParameter("physicalConditions", searchQuery.getPhysicalConditions());
            queryCount.setParameter("physicalConditions", searchQuery.getPhysicalConditions());
        }

        queryNative.setParameter("min_rating", searchQuery.getMinRating());
        queryNative.setParameter("max_rating", searchQuery.getMaxRating());

        queryCount.setParameter("min_rating", searchQuery.getMinRating());
        queryCount.setParameter("max_rating", searchQuery.getMaxRating());

        queryNative.setParameter("state", "PUBLIC");
        queryCount.setParameter("state", "PUBLIC");

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
        if(list.isEmpty())
            return Optional.of(new PageImpl(new ArrayList<>(), 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        // Get the AssetInstances that match those IDs for given page
        final TypedQuery<AssetInstanceImpl> query = em.createQuery("FROM AssetInstanceImpl AS ai WHERE id IN (:ids) " + orderByORM, AssetInstanceImpl.class);
        query.setParameter("ids", list);
        List<AssetInstanceImpl> assetInstances = query.getResultList();

        return Optional.of(new PageImpl(assetInstances, pageNum, totalPages, new ArrayList<>(), getLanguages(ids), getPhysicalConditions(ids)));
    }

    private List<String> getLanguages(List<Long> ids){
        String queryString = "SELECT DISTINCT ai.book.language FROM AssetInstanceImpl AS ai WHERE ai.assetState = :state AND ai.id IN (:ids)";

        TypedQuery<String> query = em.createQuery(queryString, String.class);
        query.setParameter("ids", ids);
        query.setParameter("state", AssetState.PUBLIC);

        return query.getResultList();

    }

    private List<String> getPhysicalConditions(List<Long> ids){
        String queryString = "SELECT DISTINCT ai.physicalCondition FROM AssetInstanceImpl AS ai WHERE ai.assetState = :state AND ai.id IN (:ids)";

        TypedQuery<PhysicalCondition> query = em.createQuery(queryString, PhysicalCondition.class);
        query.setParameter("ids", ids);
        query.setParameter("state", AssetState.PUBLIC);

        return query.getResultList().stream().map(Enum::name).collect(Collectors.toList());

    }

    private List<PhysicalCondition> getPhysicalConditionsList(List<String> list){
        return list.stream().map(PhysicalCondition::fromString).collect(Collectors.toList());
    }

    private String getPostgresFromSort(Sort sort) {

        switch (sort) {
            case TITLE_NAME:
                return "b.title";
            case AUTHOR_NAME:
                return "b.author";
            case RECENT:
                return "ai.id";
        }
        return "ai.id";
    }

    private String getOrmFromSort(Sort sort) {

        switch (sort) {
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
