package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AssetAvailabilityDaoTest {
    @Autowired
    private DataSource ds;

    @Autowired
    private AssetAvailabilityDao assetAvailabilityDao;

    private static int assetInstanceId = 1;
    private final static int userId = 1;
    private final static LocalDate borrowDate = LocalDate.now();
    private final static LocalDate devolutionDate = LocalDate.now().plusDays(7);
    private JdbcTemplate jdbcTemplate;

    @Rollback
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void borrowAssetInstanceTest() {
        //2
        int id = assetAvailabilityDao.borrowAssetInstance(assetInstanceId, userId, borrowDate, devolutionDate);
        //3
        Assert.assertEquals(id, 1);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lendings", String.format("assetinstanceid = '%s'", 1)));
    }

}
