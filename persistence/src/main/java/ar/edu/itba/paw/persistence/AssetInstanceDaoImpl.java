package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AssetInstanceDaoImpl implements AssetInstanceDao {
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<AssetInstance> ROW_MAPPER_AI = new RowMapper<AssetInstance>() {
        @Override
        public AssetInstance mapRow(ResultSet rs, int i) throws SQLException {
            String isbn = rs.getString("isbn");
            String author = rs.getString("author");
            String title = rs.getString("title");
            String language = rs.getString("language");
            Integer bookId = rs.getInt("book_id");
            Book book = new BookImpl(isbn, author, title, language);

            String zipcode = rs.getString("zipcode");
            String locality = rs.getString("locality");
            String province = rs.getString("province");
            String country = rs.getString("country");
            Integer locId = rs.getInt("loc_id");

            Location loc = new LocationImpl(locId, zipcode, locality, province, country);

            String email = rs.getString("email");
            Integer userId = rs.getInt("user_id");
            User user = new UserImpl(userId, email, "", "");

            Integer id = rs.getInt("id");
            Integer imgId = rs.getInt("photo_id");

            PhysicalCondition physicalcondition = PhysicalCondition.fromString(rs.getString("physicalcondition"));
            AssetState aState = AssetState.fromString(rs.getString("status"));

            return new AssetInstanceImpl(id, book, physicalcondition, user, loc, imgId, aState);
        }
    };
    private static final RowMapper<Integer> ROW_MAPPER_UID = (rs, rownum) -> rs.getInt("uid");

    private static final RowMapper<AssetInstance> ROW_MAPPER_BOOK = (rs, rownum) ->
            new AssetInstanceImpl(
                    rs.getInt("id"),
                    new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language")),
                    PhysicalCondition.fromString(rs.getString("physicalcondition")),
                    new UserImpl(rs.getInt("user_id"),rs.getString("email"), rs.getString("user_name"), "X"),
                    new LocationImpl(rs.getInt("loc_id"),rs.getString("zipcode"), rs.getString("locality"), rs.getString("province"), rs.getString("country")),
                    rs.getInt("photo_id"),
                    AssetState.fromString(rs.getString("status"))
                    );

//new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language")
    @Autowired
    public AssetInstanceDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public Optional<Integer> addAssetInstance(int assetId,int ownerId,int locationId,int photoId, AssetInstance ai) {
        String query = "INSERT INTO assetinstance(assetid,owner,locationid,physicalcondition,status,photoid) VALUES(?,?,?,?,?,?)";


        jdbcTemplate.update(query,assetId,ownerId,locationId,ai.getPhysicalCondition().toString(),ai.getAssetState().name(),photoId);

        return Optional.empty();
    }

    @Override
    public Optional<AssetInstance> getAssetInstance(int assetId) {
        AssetInstance assetInstance;
        try {
            Object[] params = new Object[] {assetId};
            String query = "SELECT ai.id AS id, ai.photoid AS photo_id, ai.status AS status," +
                    " ai.physicalcondition, b.uid AS book_id, b.title AS title, b.isbn AS isbn," +
                    " b.language AS language, b.author AS author, l.id AS loc_id, l.locality AS locality," +
                    " l.zipcode AS zipcode, l.province AS province, l.country AS country, u.id AS user_id ," +
                    " u.mail AS email, u.telephone,u.name as user_name  FROM assetinstance ai JOIN book b ON ai.assetid = b.uid" +
                    " JOIN location l ON ai.locationid = l.id LEFT JOIN users u ON ai.owner = u.id " +
                    "WHERE ai.id = ?";
            assetInstance = jdbcTemplate.queryForObject(query, params, ROW_MAPPER_AI);
            return Optional.of(assetInstance);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage){

        int from = pageNum * itemsPerPage;
        int to = from + itemsPerPage;

//        String query = "SELECT t.id AS id, ai.photoid AS photo_id, ai.status AS status," +
//                " ai.physicalcondition, b.uid AS book_id, b.title AS title, b.isbn AS isbn," +
//                " b.language AS language, b.author AS author, l.id AS loc_id, l.locality AS locality," +
//                " l.zipcode AS zipcode, l.province AS province, l.country AS country, u.id AS user_id," +
//                " u.mail AS email, u.telephone,u.name as user_name, CEIL(COUNT(*) OVER ()::float / 10) AS total_pages FROM ( select * from assetinstance ai JOIN book b ON ai.assetid = b.uid " +
//                "JOIN location l ON ai.locationid = l.id LEFT JOIN users u ON ai.owner = u.id WHERE ai.status=? ) as t LIMIT ? OFFSET ?";

        String query = "SELECT t.id as id, t.photoid as photo_id, t.status as status, t.physicalcondition as physicalcondition,\n" +
                "    t.book_id as book_id, t.title as title, t.isbn as isbn, t.language as language, t.author as author,\n" +
                "    t.loc_id as loc_id, t.locality as locality, t.zipcode as zipcode, t.province as province, t.country as country,\n" +
                "    t.user_id as user_id, t.mail as mail, t.telephone as telephone, t.user_name as use_name,\n" +
                "    CEIL(COUNT(*) OVER ()::float / 10) as total_pages\n" +
                "FROM (\n" +
                "    SELECT ai.id, ai.photoid, ai.status, ai.physicalcondition,\n" +
                "        b.uid AS book_id, b.title, b.isbn, b.language, b.author,\n" +
                "        l.id AS loc_id, l.locality, l.zipcode, l.province, l.country,\n" +
                "        u.id AS user_id, u.mail, u.telephone, u.name AS user_name,\n" +
                "        ROW_NUMBER() OVER (ORDER BY ai.id) AS row_number\n" +
                "    FROM assetinstance ai\n" +
                "    JOIN book b ON ai.assetid = b.uid\n" +
                "    JOIN location l ON ai.locationid = l.id\n" +
                "    LEFT JOIN users u ON ai.owner = u.id\n" +
                "    WHERE ai.status = ?\n" +
                ") t\n" +
                "WHERE t.row_number BETWEEN ? AND ?;\n";

        List<AssetInstance> assets = jdbcTemplate.query(query, ROW_MAPPER_BOOK,AssetState.PUBLIC.name(), from, to);
        Page page = new PageImpl(assets, 1, 1);
        return Optional.of(page);
    }

    @Override
    public Boolean changeStatus(int assetInstanceId, AssetState as) {
        String query = "UPDATE assetInstance SET status = ? WHERE id = ?";
        try {
            jdbcTemplate.update(query,as.name(),assetInstanceId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
