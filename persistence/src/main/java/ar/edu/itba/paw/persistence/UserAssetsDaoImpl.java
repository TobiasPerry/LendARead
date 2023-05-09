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
    public List<BorrowedAssetInstance> getLendedAssets(String email) {
        String query = "SELECT " +
                "    l.assetinstanceid," +
                "    u.name AS borrower_name," +
                "    l.devolutiondate" +
                " FROM" +
                "    lendings l" +
                " JOIN" +
                "    assetinstance ai ON l.assetinstanceid = ai.id" +
                " JOIN" +
                "    users u ON l.borrowerid = u.id" +
                " JOIN" +
                "    users owner ON ai.owner = owner.id" +
                " WHERE" +
                "    owner.mail = ?";

        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            int assetId = rs.getInt("assetinstanceid");
            String dueDate = rs.getTimestamp("devolutiondate").toString();
            String borrower = rs.getString("borrower_name");

            return assetInstanceDao.getAssetInstance(assetId)
                    .map(assetInstance -> new BorrowedAssetInstanceImpl(assetInstance, dueDate, borrower))
                    .orElse(null);
        };

        return jdbcTemplate.query(query, rowMapper, email);
    }

    @Override
    public List<BorrowedAssetInstance> getBorrowedAssets(String email) {
        String query = "SELECT " +
                "    l.assetinstanceid," +
                "    owner.name AS owner_name," +
                "    l.devolutiondate" +
                " FROM" +
                "    lendings l" +
                " JOIN" +
                "    assetinstance ai ON l.assetinstanceid = ai.id" +
                " JOIN" +
                "    users u ON l.borrowerid = u.id" +
                " JOIN" +
                "    users owner ON ai.owner = owner.id" +
                " WHERE" +
                "    u.mail = ?";

        RowMapper<BorrowedAssetInstance> rowMapper = (rs, rowNum) -> {
            int assetId = rs.getInt("assetinstanceid");
            String dueDate = rs.getTimestamp("devolutiondate").toString();
            String owner = rs.getString("owner_name");

            return assetInstanceDao.getAssetInstance(assetId)
                    .map(assetInstance -> new BorrowedAssetInstanceImpl(assetInstance, dueDate, owner))
                    .orElse(null);
        };

        List<BorrowedAssetInstance> results = jdbcTemplate.query(query, rowMapper, email);
        return results.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<AssetInstance> getUsersAssets(String email) {
        String query = "SELECT " +
                        "    ai.id" +
                        " FROM" +
                        "    assetinstance ai" +
                        " JOIN" +
                        "    users u ON ai.owner = u.id" +
                        " WHERE" +
                        "    u.mail = ?" +
                        " AND NOT EXISTS (" +
                        "     SELECT *" +
                        "     FROM" +
                        "         lendings l" +
                        "     WHERE" +
                        "         l.assetinstanceid = ai.id" +
                        " )";

        RowMapper<Integer> assetIdRowMapper = (rs, rowNum) -> rs.getInt("id");
        List<Integer> assetIds = jdbcTemplate.query(query, assetIdRowMapper, email);

        return assetIds.stream()
                .map(assetInstanceDao::getAssetInstance)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowedAssetInstance> getLendedAssetsFilteredBy(String email, String atribuite) {
        return null;
    }


}
