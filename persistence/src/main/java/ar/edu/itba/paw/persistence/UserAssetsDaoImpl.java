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

    private String matchFilterValue(String filterAtribuite) {
        return filterAtribuite.toUpperCase();
    }

    private String matchSortAttribuite(String sortAtribuite) {
        if(sortAtribuite.equals("book_name")) return "b.title";
        if(sortAtribuite.equals("expected_retrieval_date")) return "l.devolutiondate";
        if(sortAtribuite.equals("borrower_name")) return "u.name";
        if(sortAtribuite.equals("author")) return "b.author";
        if(sortAtribuite.equals("language")) return "b.language";
        return "none";
    }

    @Override
    public List<BorrowedAssetInstance> getLendedAssets(final String email, final String filterAttribute,  final String filterValue, final String sortAttribute, final String direction) {
        String query = "SELECT " +
                "    l.assetinstanceid," +
                "    u.name AS borrower_name," +
                "    l.devolutiondate" +
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
                "    owner.mail = ?";

        if (!filterAttribute.equalsIgnoreCase("none"))
            query += " AND " + filterAttribute + " = ?";
        if (!matchSortAttribuite(sortAttribute).equalsIgnoreCase("none"))
            query += " ORDER BY " + matchSortAttribuite(sortAttribute);
        if (!direction.equalsIgnoreCase("none"))
            query += " " + direction;

        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            int assetId = rs.getInt("assetinstanceid");
            String dueDate = rs.getTimestamp("devolutiondate").toString();
            String borrower = rs.getString("borrower_name");

            return assetInstanceDao.getAssetInstance(assetId)
                    .map(assetInstance -> new BorrowedAssetInstanceImpl(assetInstance, dueDate, borrower))
                    .orElse(null);
        };
        if (!filterAttribute.equalsIgnoreCase("none"))
            return jdbcTemplate.query(query, rowMapper, email, matchFilterValue(filterValue));

        return jdbcTemplate.query(query, rowMapper, email);
    }


    @Override
    public List<BorrowedAssetInstance> getBorrowedAssets(final String email, final String filterAttribute,  final String filterValue, final String sortAttribute, final String direction) {
        String query = "SELECT " +
                "    l.assetinstanceid," +
                "    owner.name AS owner_name," +
                "    l.devolutiondate" +
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
                "    u.mail = ?";

        if (!filterAttribute.equalsIgnoreCase("none"))
            query += " AND " + filterAttribute + " = ?";
        if (!matchSortAttribuite(sortAttribute).equalsIgnoreCase("none"))
            query += " ORDER BY " + matchSortAttribuite(sortAttribute);
        if (!direction.equalsIgnoreCase("none"))
            query += " " + direction;


        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            int assetId = rs.getInt("assetinstanceid");
            String dueDate = rs.getTimestamp("devolutiondate").toString();
            String owner = rs.getString("owner_name");

            return assetInstanceDao.getAssetInstance(assetId)
                    .map(assetInstance -> new BorrowedAssetInstanceImpl(assetInstance, dueDate, owner))
                    .orElse(null);
        };
        if (!filterAttribute.equalsIgnoreCase("none"))
            return jdbcTemplate.query(query, rowMapper, email, matchFilterValue(filterValue));

        return jdbcTemplate.query(query, rowMapper, email);
    }


    @Override
    public List<AssetInstance> getUsersAssets(final String email, final String filterAttribute, final String filterValue, final String sortAttribute, final String direction) {
        String query = "SELECT " +
                "    ai.id" +
                " FROM" +
                "    assetinstance ai" +
                " JOIN" +
                "    users u ON ai.owner = u.id" +
                " JOIN" +
                "    book b ON ai.assetid = b.uid" +
                " WHERE" +
                "    u.mail = ?" +
                " AND NOT EXISTS (" +
                "     SELECT *" +
                "     FROM" +
                "         lendings l" +
                "     WHERE" +
                "         l.assetinstanceid = ai.id" +
                " )";

        if (!filterAttribute.equalsIgnoreCase("none"))
            query += " AND " + filterAttribute + " = ?";
        if (!matchSortAttribuite(sortAttribute).equalsIgnoreCase("none"))
            query += " ORDER BY " + matchSortAttribuite(sortAttribute);
        if (!direction.equalsIgnoreCase("none"))
            query += " " + direction;

        System.out.println(query);

        RowMapper<Integer> assetIdRowMapper = (rs, rowNum) -> rs.getInt("id");

        List<Integer> assetIds;
        if (!filterAttribute.equalsIgnoreCase("none"))
            assetIds = jdbcTemplate.query(query, assetIdRowMapper, email, matchFilterValue(filterValue));
        else
            assetIds = jdbcTemplate.query(query, assetIdRowMapper, email);

        return assetIds.stream()
                .map(assetInstanceDao::getAssetInstance)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


}
