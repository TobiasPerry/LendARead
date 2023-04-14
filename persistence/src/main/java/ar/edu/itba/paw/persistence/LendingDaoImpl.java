package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.LendingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Date;

@Repository
public class LendingDaoImpl implements LendingDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LendingDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public int createLending(AssetInstance ai, User us, Date devolutionDate) {
        String query = "INSERT INTO lendings(assetinstanceid,borrowerId,lendDate,devolutionDate) VALUES(?,?,?,?)";

        jdbcTemplate.update(query,new Object[]{ai.getId(),us.getId(), LocalDate.now(),devolutionDate});

        return 0;
    }
}
