package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
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

    private final static SearchQuery searchQuery = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "");
    private final static SearchQuery searchQueryWithAuthorText = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "SHOW DOG");

    private final static UserImpl USER = new UserImpl(0,"EMAIL", "NAME", "TELEPHONE", "PASSWORD_NOT_ENCODED", Behaviour.BORROWER);
    private final static BookImpl BOOK = new BookImpl(0, "ISBN", "AUTHOR", "TITLE", "LANGUAGE");
    private final static LocationImpl LOCATION = new LocationImpl(0, "NAME","ZIPCODE", "LOCALITY", "PROVINCE", "COUNTRY",USER);
    private final static AssetInstanceImpl ASSET_INSTANCE_TO_CREATE = new AssetInstanceImpl(BOOK, PhysicalCondition.ASNEW, USER, LOCATION, null, AssetState.PUBLIC, 7, "DESCRIPTION",true);
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
       Optional<AssetInstanceImpl> assetInstance = assetInstanceDaoJpa.getAssetInstance(0);
       //3
        Assert.assertTrue(assetInstance.isPresent());
        Assert.assertEquals(0,assetInstance.get().getId());
        Assert.assertEquals(BOOK_TITLE_ALREADY_EXIST,assetInstance.get().getBook().getName());
    }
    @Rollback
    @Test
    public void addAssetInstanceTest(){
        ASSET_INSTANCE_TO_CREATE.setLocation(em.find(LocationImpl.class,0));
        ASSET_INSTANCE_TO_CREATE.setUserReference(em.find(UserImpl.class,0L));
        ASSET_INSTANCE_TO_CREATE.setBook(em.find(BookImpl.class,0));
        ASSET_INSTANCE_TO_CREATE.setImage(em.find(ImageImpl.class,0));

        //2
        AssetInstanceImpl assetInstance = assetInstanceDaoJpa.addAssetInstance(ASSET_INSTANCE_TO_CREATE);
        //3
        Assert.assertEquals(1,assetInstance.getId());
    }
    @Rollback
    @Test
    public void getAssetInstanceNotExistsTest(){
        //2
        Optional<AssetInstanceImpl> assetInstance = assetInstanceDaoJpa.getAssetInstance(2);
        //3
        Assert.assertFalse(assetInstance.isPresent());
    }
    @Rollback
    @Test
    public void getAllAssetInstancesTest() {
        //2
        Optional<Page> page = assetInstanceDaoJpa.getAllAssetInstances(1, 1, searchQuery);

        //3
        Assert.assertTrue(page.isPresent());
        Assert.assertEquals(1,page.get().getBooks().size());
        Assert.assertEquals(1,page.get().getPhysicalConditions().size());
        Assert.assertEquals(1,page.get().getLanguages().size());
        Assert.assertEquals(1,page.get().getTotalPages());
        Assert.assertEquals(1,page.get().getCurrentPage());
    }
    @Rollback
    @Test
    public void getAllAssetInstancesButEmptyTest() {
        //1
        em.createQuery("DELETE FROM AssetInstanceImpl WHERE id = 0").executeUpdate();
        //2
        Optional<Page> page = assetInstanceDaoJpa.getAllAssetInstances(1, 1, searchQuery);
        //3
        Assert.assertTrue(page.isPresent());
        Assert.assertEquals(0,page.get().getCurrentPage());
        Assert.assertEquals(0,page.get().getTotalPages());
    }
    @Rollback
    @Test
    public void getAllAssetInstancesSearchEmptyTest() {
        //2
       // Optional<Page> page = assetInstanceDaoJpa.getAllAssetInstances(1, 1, searchQueryWithAuthorText);
        //3
        /*Assert.assertTrue(page.isPresent());
        Assert.assertEquals(0,page.get().getCurrentPage());
        Assert.assertEquals(0,page.get().getTotalPages());*/
    }

}
