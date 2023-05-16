package ar.edu.itba.paw.services;


import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {


    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserServiceImpl us;

    private static final String EMAIL = "EMAIL";
    private static final Behaviour BEHAVIOUR = Behaviour.BORROWER;


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
    public void changeRoleTest() throws UserNotFoundException {
        // 1 - Precondiciones
        when(userDao.changeRole(anyString(), any())).thenReturn(false);

        // 2 - Ejercitación
        us.changeRole(EMAIL, BEHAVIOUR);

        // 3 - Assertions
        Assert.fail();
    }

}
