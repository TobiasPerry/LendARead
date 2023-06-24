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
import ar.edu.itba.paw.persistence.jpa.AssetInstanceDaoJpa;
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

    private final static SearchQuery searchQuery = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "",1 ,5);
    private final static SearchQuery searchQueryWithAuthorText = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "SHOW DOG",1 ,5);

    private final static String AUTHOR = "AUTHOR";
    private final static String TITLE = "TITLE";
    private final static String LANGUAGE = "LANGUAGE";
    private final static String ISBN_ALREADY_EXIST = "ISBN";
    private final static BookImpl book = new BookImpl(1, ISBN_ALREADY_EXIST, AUTHOR, TITLE, LANGUAGE);
    private final static LocationImpl location = new LocationImpl(1,"", "ZIPCODE","LOCALITY","PROVINCE","COUNTRY", null);
    private final static UserImpl user = new UserImpl(1,"EMAIL","NAME", "TELEPHONE", "PASSWORD_NOT_ENCODED", Behaviour.BORROWER);
    // private final static AssetInstanceImpl ASSET_INSTANCE_TO_CREATE = new AssetInstanceImpl(book, PhysicalCondition.ASNEW,user,location,new ImageImpl(), AssetState.PUBLIC,10);
     private final static AssetInstanceImpl ASSET_INSTANCE_TO_CREATE = new AssetInstanceImpl(-1,book, PhysicalCondition.ASNEW,user,location,new ImageImpl(), AssetState.PUBLIC,10,"desc");
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
       Optional<AssetInstanceImpl> assetInstance = assetInstanceDaoJpa.getAssetInstance(1);
       //3
        Assert.assertTrue(assetInstance.isPresent());
        Assert.assertEquals(1,assetInstance.get().getId());
        Assert.assertEquals(BOOK_TITLE_ALREADY_EXIST,assetInstance.get().getBook().getName());
    }
    @Rollback
    @Test
    public void addAssetInstanceTest(){
        //2
        AssetInstanceImpl assetInstance = assetInstanceDaoJpa.addAssetInstance(ASSET_INSTANCE_TO_CREATE);
        //3
        Assert.assertEquals(2,assetInstance.getId());
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
        jdbcTemplate.update("DELETE FROM assetinstance WHERE id = 1");
        //2
        Optional<Page> page = assetInstanceDaoJpa.getAllAssetInstances(1, 1, searchQuery);
        //3
        Assert.assertTrue(page.isPresent());
        Assert.assertEquals(1,page.get().getCurrentPage());
        Assert.assertEquals(0,page.get().getTotalPages());
    }
    @Rollback
    @Test
    public void getAllAssetInstancesSearchEmptyTest() {
        //2
        Optional<Page> page = assetInstanceDaoJpa.getAllAssetInstances(1, 1, searchQueryWithAuthorText);
        //3
        Assert.assertTrue(page.isPresent());
        Assert.assertEquals(1,page.get().getCurrentPage());
        Assert.assertEquals(0,page.get().getTotalPages());
    }

}
