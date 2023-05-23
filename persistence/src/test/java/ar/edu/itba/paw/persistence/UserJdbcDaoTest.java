package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetTokenImpl;
import ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.sql.UserDaoImpl;
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
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserJdbcDaoTest {

    private static final String PASSWORD = "PASSWORD";
    private static final String EMAIL = "EMAIL2";
    private static final String NAME = "NAME";
    private static final String TELEPHONE = "TELEPHONE";
    private static final String ALREADY_EXISTS_EMAIL = "EMAIL";

    private static final PasswordResetToken PASSWORD_RESET_TOKEN_USER_NOT_EXISTS = new PasswordResetTokenImpl("TOKEN2",EMAIL, LocalDate.now());
    private static final PasswordResetToken PASSWORD_RESET_TOKEN_USER_EXISTS = new PasswordResetTokenImpl("TOKEN2",ALREADY_EXISTS_EMAIL, LocalDate.now());

    private static final String TOKEN_ALREADY_EXISTS = "TOKEN";
    private static final int TOKEN_ID_ALREADY_EXISTS = 1;
    private static final LocalDate TOKEN_EXPIRATION_DATE_ALREADY_EXISTS = LocalDate.parse("2010-07-16");

    @Autowired
    private DataSource ds;

    @Autowired
    private UserDaoImpl userDao;


    private JdbcTemplate jdbcTemplate;

    @Rollback
    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void getUser(){
        //2
        final Optional<User> user = userDao.getUser(ALREADY_EXISTS_EMAIL);

        //3 - Asserts
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_EMAIL, user.get().getEmail());
    }
    @Rollback
    @Test
    public void testRegister(){

        //2
        final User user = userDao.addUser(Behaviour.BORROWER,EMAIL, NAME, TELEPHONE,PASSWORD);

        //3
        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", String.format("mail = '%s'", EMAIL)));
    }

    @Rollback
    @Test
    public void testChangeRole(){
        //2
        final boolean hasChange = userDao.changeRole(ALREADY_EXISTS_EMAIL,Behaviour.LENDER);

        //3
        Assert.assertTrue(hasChange);
    }
    @Rollback
    @Test
    public void getUserById(){
        //2
        final Optional<User> user = userDao.getUser(1);

        //3
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_EMAIL,user.get().getEmail());
    }

    @Rollback
    @Test
    public void changePassword(){
        //1
        final String newPassword = "NewPassword";
        //2
        final boolean hasChange = userDao.changePassword(ALREADY_EXISTS_EMAIL,newPassword);
        //3
        Assert.assertTrue(hasChange);
    }

    @Rollback
    @Test
    public void setForgotPasswordTokenUserNotExists(){
        //2
        final boolean hasChange = userDao.setForgotPasswordToken(PASSWORD_RESET_TOKEN_USER_NOT_EXISTS);

        //3
        Assert.assertFalse(hasChange);
    }
    @Rollback
    @Test
    public void setForgotPasswordTokenUser(){
        //2
        final boolean hasChange = userDao.setForgotPasswordToken(PASSWORD_RESET_TOKEN_USER_EXISTS);

        //3
        Assert.assertTrue(hasChange);
    }

    @Rollback
    @Test
    public void getPasswordRestToken(){
        //2
        final Optional<PasswordResetToken> passwordResetToken = userDao.getPasswordRestToken(TOKEN_ALREADY_EXISTS);

        //3
        Assert.assertTrue(passwordResetToken.isPresent());
        Assert.assertEquals(TOKEN_ALREADY_EXISTS,passwordResetToken.get().getToken());
        Assert.assertEquals(ALREADY_EXISTS_EMAIL,passwordResetToken.get().getUser());
        Assert.assertEquals(TOKEN_EXPIRATION_DATE_ALREADY_EXISTS,passwordResetToken.get().getExpiryDate());

    }

}
