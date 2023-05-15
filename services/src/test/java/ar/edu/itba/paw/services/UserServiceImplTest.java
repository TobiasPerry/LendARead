package ar.edu.itba.paw.services;


import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.Assert;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {



    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserServiceImpl us;
    @Mock
    private PasswordEncoder passwordEncoder;

    private static final int ID = 0;
    private static final String EMAIL = "EMAIL";
    private static final String NAME = "NAME";
    private static final String TELEPHONE = "TELEPHONE";
    private static final String PASSWORD = "PASSWORD";
    private static final Behaviour BEHAVIOUR = Behaviour.BORROWER;

    private static final User USER = new UserImpl(ID, EMAIL, NAME, TELEPHONE, PASSWORD, BEHAVIOUR);

    @Test
    public void testCreateUser(){
        // 1 - Precondiciones
        when(passwordEncoder.encode(anyString())).thenReturn(PASSWORD);
        when(userDao.addUser( any(), anyString(),  anyString(),  anyString(), anyString())).thenReturn(new UserImpl(ID,EMAIL,NAME,TELEPHONE,PASSWORD,Behaviour.BORROWER));

        // 2 - Ejercitación
        User user = us.createUser(EMAIL,NAME, TELEPHONE, PASSWORD);

        // 3 - Assertions
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getEmail(), EMAIL);
        Assert.assertEquals(user.getName(), NAME);
        Assert.assertEquals(user.getTelephone(),TELEPHONE);
        Assert.assertEquals(user.getPassword(), passwordEncoder.encode(PASSWORD));
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserTest() throws UserNotFoundException {
        // 1 - Precondiciones
        when(userDao.getUser(anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        us.getUser(EMAIL);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = UserNotFoundException.class)
    public void changeRoleTest() throws UserNotFoundException{
        // 1 - Precondiciones
        when(userDao.changeRole(anyString(), any())).thenReturn(false);

        // 2 - Ejercitación
        us.changeRole(EMAIL, BEHAVIOUR);

        // 3 - Assertions
        Assert.fail();
    }

}
