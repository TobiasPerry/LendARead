package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.assetExistanceContext.Language;
import ar.edu.itba.paw.models.assetExistanceContext.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.Behaviour;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.edu.itba.paw.persistence.config.TestConfig;
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
import java.util.ArrayList;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AssetInstanceDaoTest {
    @Autowired
    private DataSource ds;

    @Autowired
    private AssetInstanceDaoJpa assetInstanceDaoJpa;
    @PersistenceContext
    private EntityManager em;

    private final static SearchQuery searchQuery = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "",1 ,5,-1,AssetState.PUBLIC);
    private final static SearchQuery searchQueryWithAuthorText = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "SHOW DOG",1 ,5,-1,AssetState.PUBLIC);

    private final static User USER = new User(0,"EMAIL", "NAME", "TELEPHONE", "PASSWORD_NOT_ENCODED", Behaviour.BORROWER,"LOCALE");
    private final static Asset BOOK = new Asset((long)0, "ISBN", "AUTHOR", "TITLE", new Language());
    private final static Location LOCATION = new Location(0, "NAME","ZIPCODE", "LOCALITY", "PROVINCE", "COUNTRY",USER);
    private final static AssetInstance ASSET_INSTANCE_TO_CREATE = new AssetInstance(BOOK, PhysicalCondition.ASNEW, USER, LOCATION, null, AssetState.PUBLIC, 7, "DESCRIPTION",true);
    private final static String BOOK_TITLE_ALREADY_EXIST = "TITLE";
    private JdbcTemplate jdbcTemplate;

    @Rollback
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void getAssetInstanceTest(){
        //2
       Optional<AssetInstance> assetInstance = assetInstanceDaoJpa.getAssetInstance(0);
       //3
        Assert.assertTrue(assetInstance.isPresent());
        Assert.assertEquals(0,assetInstance.get().getId());
        Assert.assertEquals(BOOK_TITLE_ALREADY_EXIST,assetInstance.get().getBook().getName());
    }
    @Rollback
    @Test
    public void addAssetInstanceTest(){
        ASSET_INSTANCE_TO_CREATE.setLocation(em.find(Location.class,0));
        ASSET_INSTANCE_TO_CREATE.setUserReference(em.find(User.class,0L));
        ASSET_INSTANCE_TO_CREATE.setBook(em.find(Asset.class,0L));
        ASSET_INSTANCE_TO_CREATE.setImage(em.find(Image.class,0));

        //2
        AssetInstance assetInstance = assetInstanceDaoJpa.addAssetInstance(ASSET_INSTANCE_TO_CREATE);
        //3
        Assert.assertEquals(1,assetInstance.getId());
    }
    @Rollback
    @Test
    public void getAssetInstanceNotExistsTest(){
        //2
        Optional<AssetInstance> assetInstance = assetInstanceDaoJpa.getAssetInstance(2);
        //3
        Assert.assertFalse(assetInstance.isPresent());
    }
    @Rollback
    @Test
    public void getAllAssetInstancesTest() {
        //2
        AbstractPage<AssetInstance> page = assetInstanceDaoJpa.getAllAssetInstances(1, 1, searchQuery);

        //3
        Assert.assertEquals(1,page.getTotalPages());
        Assert.assertEquals(1,page.getCurrentPage());
    }
    @Rollback
    @Test
    public void getAllAssetInstancesButEmptyTest() {
        //1
        em.createQuery("UPDATE FROM AssetInstance SET assetState = 'DELETED' WHERE id = 0").executeUpdate();
        //2
        AbstractPage<AssetInstance> page = assetInstanceDaoJpa.getAllAssetInstances(1, 1, searchQuery);
        //3
        Assert.assertEquals(1,page.getCurrentPage());
        Assert.assertEquals(0,page.getTotalPages());
    }

}
