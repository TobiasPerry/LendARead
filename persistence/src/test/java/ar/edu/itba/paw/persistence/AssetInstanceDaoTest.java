package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
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
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
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
    private AssetInstanceDao assetInstanceDao;

    private final static SearchQuery searchQuery = new SearchQueryImpl(new ArrayList<>(),new ArrayList<>(),"");
    private JdbcTemplate jdbcTemplate;
    @Rollback
    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
    }
    @Rollback
    @Test
    public void getAllAssetInstancesTest(){
        //2
        Optional<Page> page = assetInstanceDao.getAllAssetInstances(1,1,searchQuery);

        //3
        Assert.assertTrue(page.isPresent());
    }
}
