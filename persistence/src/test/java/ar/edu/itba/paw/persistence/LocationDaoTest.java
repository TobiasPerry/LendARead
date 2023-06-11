package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class LocationDaoTest {
    @Autowired
    DataSource ds;

    @Autowired
    LocationDao locationDao;

    private static String ZIPCODE = "ZIPCODE";
    private static String LOCALITY = "LOCALITY";
    private static String PROVINCE = "PROVINCE";
    private static String COUNTRY = "COUNTRY";
    private static LocationImpl LOCATION = new LocationImpl(-1,"", ZIPCODE, LOCALITY, PROVINCE, COUNTRY, null);

    @Rollback
    @Test
    public void addLocationTest() {
        //2
        LocationImpl added = locationDao.addLocation(LOCATION);

        //3
        Assert.assertTrue(added.getId() == 2);
    }
}
