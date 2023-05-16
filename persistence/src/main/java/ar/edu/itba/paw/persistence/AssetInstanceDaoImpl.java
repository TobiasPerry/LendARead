package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.Sort;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Repository
public class AssetInstanceDaoImpl implements AssetInstanceDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<AssetInstance> ROW_MAPPER_AI = new RowMapper<AssetInstance>() {
        @Override
        public AssetInstance mapRow(ResultSet rs, int i) throws SQLException {
            String isbn = rs.getString("isbn");
            String author = rs.getString("author");
            String title = rs.getString("title");
            String language = rs.getString("lang");
            int bookId = rs.getInt("book_id");
            Book book = new BookImpl(bookId,isbn, author, title, language);

            String zipcode = rs.getString("zipcode");
            String locality = rs.getString("locality");
            String province = rs.getString("province");
            String country = rs.getString("country");
            Integer locId = rs.getInt("loc_id");

            Location loc = new LocationImpl(locId, zipcode, locality, province, country);

            String email = rs.getString("email");
            Integer userId = rs.getInt("user_id");
            String ownerName = rs.getString("user_name");
            User user = new UserImpl(userId, email, ownerName, "","", Behaviour.fromString(rs.getString("behavior")));

            int id = rs.getInt("id");
            int imgId = rs.getInt("photo_id");
            int maxWeeks = rs.getInt("maxLendingDays");
            PhysicalCondition physicalcondition = PhysicalCondition.fromString(rs.getString("physicalcondition"));
            AssetState aState = AssetState.fromString(rs.getString("status"));

            return new AssetInstanceImpl(id, book, physicalcondition, user, loc, imgId, aState,maxWeeks);
        }
    };
    private static final RowMapper<Integer> ROW_MAPPER_UID = (rs, rownum) -> rs.getInt("uid");

    private static final RowMapper<Integer> ROW_MAPPER_ROW_CANT = (rs, rownum) ->  rs.getInt("pageCount");

    private static final RowMapper<AssetInstance> ROW_MAPPER_BOOK = (rs, rownum) ->
            new AssetInstanceImpl(
                    rs.getInt("id"),
                    new BookImpl(rs.getInt("book_id"),rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("lang")),
                    PhysicalCondition.fromString(rs.getString("physicalcondition")),
                    new UserImpl(rs.getInt("user_id"),rs.getString("email"), rs.getString("user_name"), "X","",Behaviour.fromString(rs.getString("behavior"))),
                    new LocationImpl(rs.getInt("loc_id"),rs.getString("zipcode"), rs.getString("locality"), rs.getString("province"), rs.getString("country")),
                    rs.getInt("photo_id"),
                    AssetState.fromString(rs.getString("status")),
                    rs.getInt("maxLendingDays")
                    );

    private static final RowMapper<String> ROW_MAPPER_AUTHORS = (rs, rownum) -> rs.getString("author");

    private static final RowMapper<String> ROW_MAPPER_LANGUAGES = (rs, rownum) -> rs.getString("lang");

    private static final RowMapper<String> ROW_MAPPER_PHYSICAL_CONDITIONS = (rs, rownum) -> rs.getString("physicalcondition");

    @Autowired
    public AssetInstanceDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("assetinstance").usingGeneratedKeyColumns("id");

    }
    @Override
    public AssetInstance addAssetInstance(final Book book, final User owner, final Location location, final int photoId,final AssetInstance ai) {
        final Map<String, Object> args = new HashMap<>();
        args.put("assetid",book.getId());
        args.put("owner",owner.getId());
        args.put("locationid",location.getId());
        args.put("photoid",photoId);
        args.put("physicalcondition",ai.getPhysicalCondition());
        args.put("status",ai.getAssetState());
        args.put("maxLendingDays",ai.getMaxDays());

        int id = jdbcInsert.executeAndReturnKey(args).intValue();

        return new AssetInstanceImpl(id,book,ai.getPhysicalCondition(),owner,location,photoId,ai.getAssetState(),ai.getMaxDays());
    }

    @Override
    public Optional<AssetInstance> getAssetInstance(int assetId) {
        AssetInstance assetInstance;
        try {
            Object[] params = new Object[] {assetId};
            String query = "SELECT ai.id AS id, ai.photoid AS photo_id, ai.status AS status," +
                    " ai.physicalcondition, b.uid AS book_id, b.title AS title, b.isbn AS isbn," +
                    " b.lang AS lang, b.author AS author, l.id AS loc_id, l.locality AS locality," +
                    " l.zipcode AS zipcode, l.province AS province, l.country AS country, u.id AS user_id ," +
                    " u.mail AS email, u.telephone,u.name as user_name, u.behavior as behavior, ai.maxLendingDays as maxLendingDays FROM assetinstance ai JOIN book b ON ai.assetid = b.uid" +
                    " JOIN location l ON ai.locationid = l.id LEFT JOIN users u ON ai.owner = u.id " +
                    "WHERE ai.id = ?";
            assetInstance = jdbcTemplate.queryForObject(query, params, ROW_MAPPER_AI);
            return Optional.of(assetInstance);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery) {

        int offset = (pageNum - 1) * itemsPerPage;
        int limit = itemsPerPage;

        final StringBuilder queryFilters = new StringBuilder();
        List<Object> objects = new ArrayList<>();

        String query = "SELECT ai.id AS id, ai.photoid AS photo_id, ai.status AS status," +
                " ai.physicalcondition, b.uid AS book_id, b.title AS title, b.isbn AS isbn," +
                " b.lang AS lang, b.author AS author, l.id AS loc_id, l.locality AS locality," +
                " l.zipcode AS zipcode, l.province AS province, l.country AS country, u.id AS user_id," +
                " u.mail AS email, u.telephone,u.name as user_name, u.behavior as behavior, ai.maxLendingDays as maxLendingDays FROM assetinstance ai JOIN book b ON ai.assetid = b.uid " +
                "JOIN location l ON ai.locationid = l.id LEFT JOIN users u ON ai.owner = u.id WHERE status != 'DELETED' AND status=? ";
        objects.add(AssetState.PUBLIC.name());


        final List<String> languagesIn = searchQuery.getLanguages();
        if(!languagesIn.isEmpty()) {
            queryFilters.append(" AND b.lang IN (''");
            languagesIn.forEach((language) -> queryFilters.append(",").append("?"));
            queryFilters.append(")");
            objects.addAll(languagesIn);
        }
        final List<String> physicalConditionsIn = searchQuery.getPhysicalConditions();
        if(!physicalConditionsIn.isEmpty()) {
            queryFilters.append(" AND ai.physicalcondition IN (''");
            physicalConditionsIn.forEach((physicalCondition) -> queryFilters.append(",").append("?"));
            queryFilters.append(")");
            objects.addAll(physicalConditionsIn);
        }
        final String search = searchQuery.getSearch().replace("%", "\\%");
        if(!search.equals("")) {
            String[] searchParsed = search.split(" ", 0);
            queryFilters.append(" AND ( ");
            for(String searchItem : searchParsed){
                queryFilters.append(" UPPER(b.author) LIKE UPPER(").append("?").append(") ");
                objects.add("%" + searchItem +"%");
                queryFilters.append("OR");
                queryFilters.append(" UPPER(b.title) LIKE UPPER(").append("?").append(") ");
                queryFilters.append("OR");
                objects.add("%" + searchItem +"%");
            }
            queryFilters.deleteCharAt(queryFilters.length() - 1);
            queryFilters.deleteCharAt(queryFilters.length() - 1);
            queryFilters.append(" )");
        }

        String querySort = "";
        if(searchQuery.getSort() != null){
            querySort = " ORDER BY " + getPostgresFromSort(searchQuery.getSort()) + " " + getPostgresFromSortDirection(searchQuery.getSortDirection()) + " ";
        }

        String pagination = " LIMIT ? OFFSET ?";

        query = query + queryFilters + querySort + pagination ;

        objects.add(limit);
        objects.add(offset);

        List<AssetInstance> assets = jdbcTemplate.query(query, ROW_MAPPER_BOOK, objects.toArray());

        objects.remove(objects.size() - 1);
        objects.remove(objects.size() - 1);

        String queryAuthors = "SELECT distinct b.author as author FROM assetinstance ai JOIN book b ON ai.assetid = b.uid " +
                "JOIN location l ON ai.locationid = l.id LEFT JOIN users u ON ai.owner = u.id WHERE status =? " + queryFilters ;

        String queryLanguages = "SELECT distinct b.lang as lang FROM assetinstance ai JOIN book b ON ai.assetid = b.uid " +
                "JOIN location l ON ai.locationid = l.id LEFT JOIN users u ON ai.owner = u.id WHERE status=? " + queryFilters  ;

        String queryPhysicalConditions = "SELECT distinct ai.physicalcondition as physicalcondition  FROM assetinstance ai JOIN book b ON ai.assetid = b.uid " +
                "JOIN location l ON ai.locationid = l.id LEFT JOIN users u ON ai.owner = u.id WHERE status=? " + queryFilters ;

        List<String> authors = jdbcTemplate.query(queryAuthors, ROW_MAPPER_AUTHORS,objects.toArray());

        List<String> physicalConditions = jdbcTemplate.query(queryPhysicalConditions, ROW_MAPPER_PHYSICAL_CONDITIONS,objects.toArray());

        List<String> languages = jdbcTemplate.query(queryLanguages, ROW_MAPPER_LANGUAGES, objects.toArray());

        objects.add(0,itemsPerPage);

        String queryCant = "SELECT CEIL(COUNT(*) / CAST(? AS FLOAT)) as pageCount \n" +
                "FROM assetinstance ai \n" +
                "JOIN book b ON ai.assetid = b.uid \n" +
                "JOIN location l ON ai.locationid = l.id \n" +
                "LEFT JOIN users u ON ai.owner = u.id \n" +
                "WHERE status = ?" + queryFilters;
        List<Integer> queryOutput = jdbcTemplate.query(queryCant, ROW_MAPPER_ROW_CANT,objects.toArray());

        int totalPages;

        if(!queryOutput.isEmpty())
            totalPages = queryOutput.get(0);
        else
            totalPages = 0;

        Page page = new PageImpl(assets, pageNum, totalPages, authors, languages, physicalConditions);

        return Optional.of(page);
    }


    @Override
    public Boolean changeStatus(final int id, final AssetState as) {
        String query = "UPDATE assetInstance SET status = ? WHERE id = ?";
        try {
            jdbcTemplate.update(query,as.name(), id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Override
    public Boolean changeStatusByLendingId(final int lendingId, final AssetState as) {
        String query = "UPDATE assetInstance AS ai " +
                "SET status = ? " +
                "FROM lendings AS l " +
                "WHERE ai.id = l.assetinstanceid AND l.id = ?";
        try {
            jdbcTemplate.update(query,as.name(), lendingId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private String getPostgresFromSort(Sort sort){

        switch (sort){
            case TITLE_NAME:
                return "b.title";
            case AUTHOR_NAME:
                return "b.author";
            case RECENT:
                return "ai.id";
        }
        return "ai.id";
    }

    private String getPostgresFromSortDirection(SortDirection sortDirection){
        switch (sortDirection){
            case ASCENDING:
                return "ASC";
            case DESCENDING:
                return "DESC";
        }
        return "ASC";
    }
}
