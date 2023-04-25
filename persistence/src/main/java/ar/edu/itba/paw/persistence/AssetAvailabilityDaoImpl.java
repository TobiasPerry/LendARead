package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AssetAvailabilityDaoImpl implements AssetAvailabilityDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AssetAvailabilityDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public boolean borrowAssetInstance(int assetInstanceId, int userId, LocalDate borrowDate,LocalDate devolutionDate) {
        String query = "INSERT INTO lendings(assetinstanceid,borrowerId,lendDate,devolutionDate) VALUES(?,?,?,?)";

        jdbcTemplate.update(query,assetInstanceId,userId, borrowDate,devolutionDate);

        return true;
    }

    @Override
    public List<LendingDetails> getAllLendings() {
        return null;
    }
}
