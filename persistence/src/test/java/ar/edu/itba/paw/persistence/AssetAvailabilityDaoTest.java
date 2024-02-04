package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.assetExistanceContext.Language;
import ar.edu.itba.paw.models.assetExistanceContext.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.assetLendingContext.LendingState;
import ar.edu.itba.paw.models.userContext.Behaviour;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AssetAvailabilityDaoTest {

    @Autowired
    private DataSource ds;
    @Autowired
    private AssetAvailabilityDao assetAvailabilityDao;

    @PersistenceContext
    private EntityManager em;
    private final static User USER = new User(0,"EMAIL", "NAME", "TELEPHONE", "PASSWORD_NOT_ENCODED", Behaviour.BORROWER,"LOCALE");
    private final static Language LANGUAGE = new Language("spa", "Español");
    private final static Asset BOOK = new Asset((long)0, "ISBN", "AUTHOR", "TITLE", LANGUAGE);
    private final static Location LOCATION = new Location(0, "LOCATION","ZIPCODE", "LOCALITY", "PROVINCE", "COUNTRY",USER);
    private final static AssetInstance ASSET_INSTANCE = new AssetInstance(0,BOOK, PhysicalCondition.ASNEW, USER, LOCATION, null, AssetState.PUBLIC, 7, "DESCRIPTION");
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
    public void testBorrowAssetInstance() {

        LendingState lendingState = LendingState.ACTIVE;

        Lending result = assetAvailabilityDao.borrowAssetInstance(ASSET_INSTANCE, USER, borrowDate, devolutionDate, lendingState);

        Assert.assertNotNull(result);

        em.flush();
        em.clear();

        Lending retrievedLending = em.find(Lending.class, result.getId());
        Assert.assertNotNull(retrievedLending);

        Assert.assertEquals(ASSET_INSTANCE.getId(), retrievedLending.getAssetInstance().getId());
        Assert.assertEquals(USER, retrievedLending.getUserReference());
        Assert.assertEquals(borrowDate, retrievedLending.getLendDate());
        Assert.assertEquals(devolutionDate, retrievedLending.getDevolutionDate());
        Assert.assertEquals(lendingState, retrievedLending.getActive());
    }
    @Rollback
    @Test
    public void testGetActiveLendings() {


        Lending activeLending1 = new Lending(ASSET_INSTANCE, USER, borrowDate, devolutionDate, LendingState.ACTIVE);
        Lending activeLending2 = new Lending(ASSET_INSTANCE, USER, borrowDate, devolutionDate, LendingState.ACTIVE);
        Lending finishedLending = new Lending(ASSET_INSTANCE, USER, borrowDate, devolutionDate, LendingState.FINISHED);

        em.persist(activeLending1);
        em.persist(activeLending2);
        em.persist(finishedLending);


        List<Lending> activeLendings = assetAvailabilityDao.getActiveLendings(ASSET_INSTANCE);

        Assert.assertEquals(4, activeLendings.size());
        Assert.assertTrue(activeLendings.contains(activeLending1));
        Assert.assertTrue(activeLendings.contains(activeLending2));
        Assert.assertFalse(activeLendings.contains(finishedLending));
    }
    @Rollback
    @Test
    public void testChangeLendingStatus() {
        Lending lending = new Lending(ASSET_INSTANCE, USER, borrowDate.plusDays(2), devolutionDate, LendingState.ACTIVE);

        em.persist(lending);



        LendingState newLendingState = LendingState.FINISHED;
        assetAvailabilityDao.changeLendingStatus(lending, newLendingState);



        Lending retrievedLending = em.find(Lending.class, lending.getId());
        Assert.assertNotNull(retrievedLending);
        Assert.assertEquals(newLendingState, retrievedLending.getActive());
    }

}
