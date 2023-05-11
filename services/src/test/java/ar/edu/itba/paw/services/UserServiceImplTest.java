package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.Assert;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {


    private static final int FIRST_ID = 0;
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserServiceImpl us;
    @Mock
    private PasswordEncoder passwordEncoder;

    private static final String EMAIL = "EMAIL";
    private static final String NAME = "NAME";
    private static final String TELEPHONE = "TELEPHONE";
    private static final String PASSWORD = "PASSWORD";

    @Test
    public void testCreateUser(){
        // 1 - Precondiciones
        when(passwordEncoder.encode(anyString())).thenReturn(PASSWORD);
        when(userDao.addUser( any(), anyString(),  anyString(),  anyString(), anyString())).thenReturn(new UserImpl(FIRST_ID,EMAIL,NAME,TELEPHONE,PASSWORD,Behaviour.BORROWER));

        // 2 - Ejercitacion
        User user = us.createUser(EMAIL,NAME, TELEPHONE, PASSWORD);

        // 3 - Assertions
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getEmail(), EMAIL);
        Assert.assertEquals(user.getName(), NAME);
        Assert.assertEquals(user.getTelephone(),TELEPHONE);
        Assert.assertEquals(user.getPassword(), passwordEncoder.encode(PASSWORD));
    }

}
