package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingDetailsImpl;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AssetAvailabilityDaoImpl implements AssetAvailabilityDao {

    private final JdbcTemplate jdbcTemplate;

    private final static RowMapper<LendingDetails> rowMapper = (rs, rownum) -> new LendingDetailsImpl(rs.getInt("borrowerId"), rs.getInt("assetinstanceid"), rs.getObject("lendDate", LocalDate.class), rs.getObject("devolutionDate", LocalDate.class));

    @Autowired
    public AssetAvailabilityDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public boolean borrowAssetInstance(int assetInstanceId, int userId, LocalDate borrowDate, LocalDate devolutionDate) {
        String query = "INSERT INTO lendings(assetinstanceid,borrowerId,lendDate,devolutionDate) VALUES(?,?,?,?)";

        jdbcTemplate.update(query, assetInstanceId, userId, borrowDate, devolutionDate);

        return true;
    }

    @Override
        public boolean setLendingFinished(final int assetInstanceId) {
            String query = "UPDATE lendings SET active = 'INACTIVE' WHERE assetinstanceid = ? AND active = 'ACTIVE'";

            int updatedRows = jdbcTemplate.update(query, assetInstanceId);

            return updatedRows == 1;
        }

    }