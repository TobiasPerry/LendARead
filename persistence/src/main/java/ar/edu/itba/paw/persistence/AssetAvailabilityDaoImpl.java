package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingDetailsImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AssetAvailabilityDaoImpl implements AssetAvailabilityDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    private final static RowMapper<LendingDetails> rowMapper = (rs, rownum) -> new LendingDetailsImpl(rs.getInt("borrowerId"), rs.getInt("assetinstanceid"), rs.getObject("lendDate", LocalDate.class), rs.getObject("devolutionDate", LocalDate.class));

    @Autowired
    public AssetAvailabilityDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("lendings").usingGeneratedKeyColumns("id");

    }

    @Override
    public int borrowAssetInstance(int assetInstanceId, int userId, LocalDate borrowDate, LocalDate devolutionDate) {
        String query = "INSERT INTO lendings(assetinstanceid,borrowerId,lendDate,devolutionDate) VALUES(?,?,?,?)";
        final Map<String, Object> args = new HashMap<>();
        args.put("assetinstanceid", assetInstanceId);
        args.put("borrowerId", userId);
        args.put("lendDate", java.sql.Date.valueOf(borrowDate));
        args.put("devolutionDate", java.sql.Date.valueOf(devolutionDate));
        args.put("active", "ACTIVE");
        return jdbcInsert.executeAndReturnKey(args).intValue();
    }

    @Override
    public boolean changeLendingStatus(final int lendingId, final LendingState lendingState) {
        String query = "UPDATE lendings SET active = ? WHERE id = ? ";
        int updatedRows = jdbcTemplate.update(query, lendingState.toString(), lendingId);
        return updatedRows == 1;
    }

}
