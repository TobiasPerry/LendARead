package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.BorrowedAssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingDetailsImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PageUserAssetsImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

//La mayoria de la logica se puede encapsular, reutilizar, y mejorar. Sin embargo como se va a pasar a una ORM
//no lo consideramos que lo valia.
@Repository
public class UserAssetsDaoImpl implements UserAssetsDao {
    private final JdbcTemplate jdbcTemplate;

    private final AssetInstanceDao assetInstanceDao;

    @Autowired
    public UserAssetsDaoImpl(final DataSource ds, AssetInstanceDao assetInstanceDao) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.assetInstanceDao = assetInstanceDao;
    }

    private String matchFilterValue(String filterAtribuite) {
        return filterAtribuite.toUpperCase();
    }

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
                return "b.language";
            default:
                return "none";
        }
    }


    public PageUserAssets getLendedAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAttribute, final String filterValue, final String sortAttribute, final String direction) {
        String query = "SELECT " +
                "    l.assetinstanceid," +
                "    u.name AS borrower_name," +
                "    l.devolutiondate," +
                "    l.id AS lendingId," +
                "    l.active AS lendingState" +
                " FROM" +
                "    lendings l" +
                " JOIN" +
                "    assetinstance ai ON l.assetinstanceid = ai.id" +
                " JOIN" +
                "    book b ON ai.assetid = b.uid" +
                " JOIN" +
                "    users u ON l.borrowerid = u.id" +
                " JOIN" +
                "    users owner ON ai.owner = owner.id" +
                " WHERE" +
                "  owner.mail = ? ";

        if (filterAttribute.equalsIgnoreCase("status"))
            query += "AND (l.active = 'DELIVERED' OR l.active = 'ACTIVE') AND status = ?";
        if(filterAttribute.equalsIgnoreCase("lendingStatus"))
            query += "AND l.active = ?";
        if(filterAttribute.equalsIgnoreCase("delayed"))
            query += "AND (l.active = 'DELIVERED' OR l.active = 'ACTIVE') AND date(l.devolutiondate) < CURRENT_DATE";
        if (!matchSortAttribuite(sortAttribute).equalsIgnoreCase("none")) {
            query += " ORDER BY " + matchSortAttribuite(sortAttribute);
            if (!direction.equalsIgnoreCase("none")) {
                query += " " + direction;
            }
        }

        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            int assetId = rs.getInt("assetinstanceid");
            String dueDate = rs.getTimestamp("devolutiondate").toString();
            String borrower = rs.getString("borrower_name");
            int lendingId = rs.getInt("lendingId");
            String lendingState = rs.getString("lendingState");


            return assetInstanceDao.getAssetInstance(assetId)
                    .map(assetInstance -> new BorrowedAssetInstanceImpl(assetInstance, dueDate, borrower, lendingId, LendingState.fromString(lendingState)))
                    .orElse(null);
        };

        if (!filterAttribute.equalsIgnoreCase("none") && !filterAttribute.equalsIgnoreCase("delayed"))
            return new PageUserAssetsImpl(jdbcTemplate.query(query, rowMapper, email, matchFilterValue(filterValue)));

        return new PageUserAssetsImpl(jdbcTemplate.query(query, rowMapper, email));
    }



    @Override
    public PageUserAssets getBorrowedAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAttribute,  final String filterValue, final String sortAttribute, final String direction) {
        String query = "SELECT " +
                "    l.assetinstanceid," +
                "    owner.name AS owner_name," +
                "    l.devolutiondate," +
                "    l.id AS lendingId," +
                "    l.active AS lendingState," +
                "u.mail AS borrower_mail"+
                " FROM" +
                "    lendings l" +
                " JOIN" +
                "    assetinstance ai ON l.assetinstanceid = ai.id" +
                " JOIN" +
                "    book b ON ai.assetid = b.uid" +
                " JOIN" +
                "    users u ON l.borrowerid = u.id" +
                " JOIN" +
                "    users owner ON ai.owner = owner.id" +
                " WHERE" +
                "  u.mail = ? ";


        if (filterAttribute.equalsIgnoreCase("status"))
            query += "AND (l.active = 'DELIVERED' OR l.active = 'ACTIVE') AND status = ?";
        if(filterAttribute.equalsIgnoreCase("lendingStatus"))
            query += "AND l.active = ?";
        if(filterAttribute.equalsIgnoreCase("delayed"))
            query += "AND (l.active = 'DELIVERED' OR l.active = 'ACTIVE') AND date(l.devolutiondate) < CURRENT_DATE";
        if (!matchSortAttribuite(sortAttribute).equalsIgnoreCase("none")) {
            query += " ORDER BY " + matchSortAttribuite(sortAttribute);
            if (!direction.equalsIgnoreCase("none")) {
                query += " " + direction;
            }
        }


        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            int assetId = rs.getInt("assetinstanceid");
            String dueDate = rs.getTimestamp("devolutiondate").toString();
            String owner = rs.getString("owner_name");
            String borrower = rs.getString("borrower_mail");

            int lendingId = rs.getInt("lendingId");
            String lendingState = rs.getString("lendingState");


            return assetInstanceDao.getAssetInstance(assetId)
                    .map(assetInstance -> new BorrowedAssetInstanceImpl(assetInstance, dueDate, borrower, lendingId, LendingState.fromString(lendingState)))
                    .orElse(null);
        };
        if (!filterAttribute.equalsIgnoreCase("none") && !filterAttribute.equalsIgnoreCase("delayed"))
            return new PageUserAssetsImpl(jdbcTemplate.query(query, rowMapper, email, matchFilterValue(filterValue)));

        return new PageUserAssetsImpl(jdbcTemplate.query(query, rowMapper, email));
    }


    @Override
    public PageUserAssets getUsersAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAttribute, final String filterValue, final String sortAttribute, final String direction) {
        int offset = (pageNumber - 1) * itemsPerPage;

        String query = "SELECT " +
                "    ai.id" +
                " FROM" +
                "    assetinstance ai" +
                " JOIN" +
                "    users u ON ai.owner = u.id" +
                " JOIN" +
                "    book b ON ai.assetid = b.uid" +
                " WHERE" +
                "  ai.status != 'DELETED' AND   u.mail = ?" +
                " AND NOT EXISTS (" +
                "     SELECT *" +
                "     FROM" +
                "         lendings l" +
                "     WHERE" +
                "         l.assetinstanceid = ai.id AND (l.active = 'DELIVERED' OR l.active = 'ACTIVE') " +
                " )";

        if (!filterAttribute.equalsIgnoreCase("none"))
            query += " AND " + filterAttribute + " = ?";
        if (!matchSortAttribuite(sortAttribute).equalsIgnoreCase("none")) {
            query += " ORDER BY " + matchSortAttribuite(sortAttribute);
            if (!direction.equalsIgnoreCase("none")) {
                query += " " + direction;
            }
        }

        query += " LIMIT ? OFFSET ?";

        System.out.println(query);

        RowMapper<Integer> assetIdRowMapper = (rs, rowNum) -> rs.getInt("id");

        List<Integer> assetIds;
        if (!filterAttribute.equalsIgnoreCase("none"))
            assetIds = jdbcTemplate.query(query, assetIdRowMapper, email, matchFilterValue(filterValue), itemsPerPage, offset);
        else
            assetIds = jdbcTemplate.query(query, assetIdRowMapper, email, itemsPerPage, offset);

        return new PageUserAssetsImpl(
                assetIds.stream()
                        .map(assetInstanceDao::getAssetInstance)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()),
                pageNumber,
                getTotalPagesUserAssets(itemsPerPage, email));
    }

    private int getTotalPagesUserAssets(int itemsPerPage, String email) {
        String countQuery = "SELECT COUNT(*)" +
                " FROM" +
                "    assetinstance ai" +
                " JOIN" +
                "    users u ON ai.owner = u.id" +
                " JOIN" +
                "    book b ON ai.assetid = b.uid" +
                " WHERE" +
                "  ai.status != 'DELETED' AND   u.mail = ?" +
                " AND NOT EXISTS (" +
                "     SELECT *" +
                "     FROM" +
                "         lendings l" +
                "     WHERE" +
                "         l.assetinstanceid = ai.id AND (l.active = 'DELIVERED' OR l.active = 'ACTIVE') " +
                " )";

        int rowCount = jdbcTemplate.queryForObject(countQuery, new Object[]{email}, Integer.class);

        return (rowCount / itemsPerPage) + ((rowCount % itemsPerPage > 0) ? 1 : 0);
    }


    @Override
    public Optional<BorrowedAssetInstance> getBorrowedAsset(int lendingId) {
        String query = "SELECT " +
                "    l.assetinstanceid," +
                "    owner.name AS owner_name," +
                "    l.devolutiondate," +
                "    l.id AS lendingId," +
                "    l.active AS lendingState," +
                "u.mail AS borrower_name "+
                " FROM" +
                "    lendings l" +
                " JOIN" +
                "    assetinstance ai ON l.assetinstanceid = ai.id" +
                " JOIN" +
                "    book b ON ai.assetid = b.uid" +
                " JOIN" +
                "    users u ON l.borrowerid = u.id" +
                " JOIN" +
                "    users owner ON ai.owner = owner.id" +
                " WHERE" +
                "    l.id = ?";

        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            int assetId = rs.getInt("assetinstanceid");
            String dueDate = rs.getTimestamp("devolutiondate").toString();
            String owner = rs.getString("owner_name");
            String borrower = rs.getString("borrower_name");
            String lendingState = rs.getString("lendingState");

            return assetInstanceDao.getAssetInstance(assetId)
                    .map(assetInstance -> new BorrowedAssetInstanceImpl(assetInstance, dueDate, borrower, lendingId, LendingState.fromString(lendingState)))
                    .orElse(null);
        };

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, rowMapper, lendingId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


}