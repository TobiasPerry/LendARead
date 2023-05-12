package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserJdbcDaoTest {

    private static final String PASSWORD = "PASSWORD";
    private static final String EMAIL = "EMAIL";
    private static final String NAME = "NAME";
    private static final String TELEPHONE = "TELEPHONE";


    @Autowired
    private DataSource ds;

    @Autowired
    private	UserDaoImpl userDao;

    @Autowired
    private	AssetInstanceDaoImpl assetInstanceDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void basicTest(){


        User userGet = userDao.getUser(1).get();
        boolean changePasswordReturnValue = userDao.changePassword(EMAIL, PASSWORD);
        User user = userDao.addUser(Behaviour.BORROWER, "a", "b", "c", "d");


    }


}
