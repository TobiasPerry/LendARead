package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.userContext.Behaviour;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class LocationDaoTest {
    @Autowired
    DataSource ds;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    LocationDao locationDao;

    private static String ZIPCODE = "ZIPCODE";
    private static String LOCALITY = "LOCALITY";
    private static String PROVINCE = "PROVINCE";
    private static String COUNTRY = "COUNTRY";
    private final static User USER = new User(0,"EMAIL", "NAME", "TELEPHONE", "PASSWORD_NOT_ENCODED", Behaviour.BORROWER,"LOCALE");

    private static Location LOCATION = new Location("", ZIPCODE, LOCALITY, PROVINCE, COUNTRY, USER);

    @Rollback
    @Test
    public void addLocationTest() {
        User user = em.find(User.class, 0L);
        LOCATION.setUser(user);
        //2
        Location added = locationDao.addLocation(LOCATION);

        //3
        Assert.assertEquals(1, added.getId());
    }
}
