package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingDetailsImpl;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Repository

public class UserAssetsDaoImpl implements UserAssetsDao {
    private final JdbcTemplate jdbcTemplate;

    private final static RowMapper<LendingDetails> rowMapper = (rs, rownum) -> new LendingDetailsImpl(rs.getInt("borrowerId"), rs.getInt("assetinstanceid"),  rs.getObject("lendDate", LocalDate.class), rs.getObject("devolutionDate", LocalDate.class));
    @Autowired
    public UserAssetsDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<BorrowedAssetInstance> getLendedAssets() {
        return null;
    }
}
