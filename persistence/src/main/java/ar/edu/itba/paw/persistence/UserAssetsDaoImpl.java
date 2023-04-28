package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.BorrowedAssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingDetailsImpl;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository

public class UserAssetsDaoImpl implements UserAssetsDao {
    private final JdbcTemplate jdbcTemplate;

    private final AssetInstanceDao assetInstanceDao;

    @Autowired
    public UserAssetsDaoImpl(final DataSource ds, AssetInstanceDao assetInstanceDao) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.assetInstanceDao = assetInstanceDao;
    }


    @Override
    public List<BorrowedAssetInstance> getBorrowedAssets(String email) {
        String query = "SELECT " +
                "    ai.id, ai.book, ai.physicalcondition, ai.owner, ai.location, ai.imageid, ai.assetstate, " +
                "    b.isbn, b.author, b.title, b.language, " +
                "    l.zipcode, l.locality, l.province, l.country, " +
                "    owner.mail, owner.name AS owner_name, owner.behavior, l.devolutiondate" +
                " FROM" +
                "    lendings l" +
                " JOIN" +
                "    assetinstance ai ON l.assetinstanceid = ai.id" +
                " JOIN" +
                "    users u ON l.borrowerid = u.id" +
                " JOIN" +
                "    users owner ON ai.owner = owner.id" +
                " JOIN" +
                "    book b ON ai.book = b.uid" +
                " JOIN" +
                "    location l ON ai.location = l.id" +
                " WHERE" +
                "    u.mail = ?";

        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            Book book = new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language"));
            Location location = new LocationImpl(rs.getInt("location"), rs.getString("zipcode"), rs.getString("locality"), rs.getString("province"), rs.getString("country"));
            User owner = new UserImpl(rs.getInt("owner"), rs.getString("mail"), rs.getString("owner_name"), "", "", Behaviour.fromString(rs.getString("behavior")));

            AssetInstance assetInstance = new AssetInstanceImpl(
                    rs.getInt("id"),
                    book,
                    PhysicalCondition.fromString(rs.getString("physicalcondition")),
                    owner,
                    location,
                    rs.getInt("imageid"),
                    AssetState.fromString(rs.getString("assetstate"))
            );

            String dueDate = rs.getTimestamp("devolutiondate").toString();
            return new BorrowedAssetInstanceImpl(assetInstance, dueDate, rs.getString("owner_name"));
        };

        return jdbcTemplate.query(query, rowMapper, email);
    }    @Override


    public List<BorrowedAssetInstance> getLendedAssets(String email) {
        String query = "SELECT " +
                "    ai.id, ai.book, ai.physicalcondition, ai.owner, ai.location, ai.imageid, ai.assetstate, " +
                "    b.isbn, b.author, b.title, b.language, " +
                "    l.zipcode, l.locality, l.province, l.country, " +
                "    u.mail, u.name AS borrower_name, u.behavior, l.devolutiondate" +
                " FROM" +
                "    lendings l" +
                " JOIN" +
                "    assetinstance ai ON l.assetinstanceid = ai.id" +
                " JOIN" +
                "    users u ON l.borrowerid = u.id" +
                " JOIN" +
                "    users owner ON ai.owner = owner.id" +
                " JOIN" +
                "    book b ON ai.book = b.uid" +
                " JOIN" +
                "    location l ON ai.location = l.id" +
                " WHERE" +
                "    owner.mail = ?";

        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            Book book = new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language"));
            Location location = new LocationImpl(rs.getInt("location"), rs.getString("zipcode"), rs.getString("locality"), rs.getString("province"), rs.getString("country"));
            User borrower = new UserImpl(rs.getInt("owner"), rs.getString("mail"), rs.getString("borrower_name"), "", "", Behaviour.fromString(rs.getString("behavior")));

            AssetInstance assetInstance = new AssetInstanceImpl(
                    rs.getInt("id"),
                    book,
                    PhysicalCondition.fromString(rs.getString("physicalcondition")),
                    borrower,
                    location,
                    rs.getInt("imageid"),
                    AssetState.fromString(rs.getString("assetstate"))
            );

            String dueDate = rs.getTimestamp("devolutiondate").toString();
            return new BorrowedAssetInstanceImpl(assetInstance, dueDate, rs.getString("borrower_name"));
        };

        return jdbcTemplate.query(query, rowMapper, email);
    }

    @Override
    public List<AssetInstance> getUsersAssets(String email) {
        String query = "SELECT " +
                "    ai.id, ai.book, ai.physicalcondition, ai.owner, ai.location, ai.imageid, ai.assetstate, " +
                "    b.isbn, b.author, b.title, b.language, " +
                "    l.zipcode, l.locality, l.province, l.country, " +
                "    u.mail, u.name AS user_name, u.behavior" +
                " FROM" +
                "    assetinstance ai" +
                " JOIN" +
                "    users u ON ai.owner = u.id" +
                " JOIN" +
                "    book b ON ai.book = b.uid" +
                " JOIN" +
                "    location l ON ai.location = l.id" +
                " WHERE" +
                "    u.mail = ?";

        RowMapper<AssetInstance> rowMapper = (rs, rowNum) -> {
            Book book = new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language"));
            Location location = new LocationImpl(rs.getInt("location"), rs.getString("zipcode"), rs.getString("locality"), rs.getString("province"), rs.getString("country"));
            User user = new UserImpl(rs.getInt("owner"), rs.getString("mail"), rs.getString("user_name"), "", "", Behaviour.fromString(rs.getString("behavior")));

            return new AssetInstanceImpl(
                    rs.getInt("id"),
                    book,
                    PhysicalCondition.fromString(rs.getString("physicalcondition")),
                    user,
                    location,
                    rs.getInt("imageid"),
                    AssetState.fromString(rs.getString("assetstate"))
            );
        };

        return jdbcTemplate.query(query, rowMapper, email);
    }


}
