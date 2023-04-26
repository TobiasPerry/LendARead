package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.BorrowedAssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingDetailsImpl;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;
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

    private final static RowMapper<LendingDetails> rowMapper = (rs, rownum) -> new LendingDetailsImpl(rs.getInt("borrowerId"), rs.getInt("assetinstanceid"),  rs.getObject("lendDate", LocalDate.class), rs.getObject("devolutionDate", LocalDate.class));
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
            Optional<AssetInstance> assetInstanceOpt = assetInstanceDao.getAssetInstance(rs.getInt("assetinstanceid"));
            if (assetInstanceOpt.isPresent()) {
                return new BorrowedAssetInstanceImpl(assetInstanceOpt.get(), rs.getTimestamp("devolutiondate").toString(),  rs.getString("borrower_name"));
            } else {
                return null;
            }
        };

        List<BorrowedAssetInstance> results = jdbcTemplate.query(query, rowMapper, email);
        return results.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<BorrowedAssetInstance> getBorrowedAssets(String email) {
        return new ArrayList<>();
    }

    @Override
    public List<AssetInstance> getUsersAssets(String email) {
        return new ArrayList<>();
    }
}
