package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetInstanceBorrowException;
import ar.edu.itba.paw.exceptions.DayOutOfRangeException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssetAvailabilityServiceImplTest {

    @Mock
    private UserDao userDao;
    @Mock
    private AssetInstanceDao assetInstanceDao;
    @Mock
    private AssetAvailabilityDao lendingDao;

    @InjectMocks
    private AssetAvailabilityServiceImpl assetAvailabilityService;

    private static final int USER_ID = 0;
    private static final int ASSET_ID = 0;
    private static final String EMAIL = "user@domain.com";
    private static final String NAME = "John Doe";
    private static final String TELEPHONE = "";
    private static final String PASSWORD_ENCODED = "";
    private static final Behaviour BEHAVIOUR = Behaviour.BORROWER;
    private static final LocalDate DEVOLUTION_DATE_OK = LocalDate.now().plusDays(5);
    private static final LocalDate DEVOLUTION_DATE_WRONG = LocalDate.now().plusDays(30);
    private static final UserImpl USER = new UserImpl(USER_ID, EMAIL, NAME, TELEPHONE, PASSWORD_ENCODED, BEHAVIOUR);
    private static final AssetInstance ASSET_INSTANCE = new AssetInstanceImpl(
            ASSET_ID,
            new BookImpl(0, "", "", "", ""),
            PhysicalCondition.ASNEW,
            USER,
            new LocationImpl(0,"", "", "", ""),
            0,
            AssetState.PUBLIC,
            10
    );


    @Test(expected = AssetInstanceBorrowException.class)
    public void borrowAssetNotPresentTest() throws Exception{
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.empty());
        when(userDao.getUser(anyString())).thenReturn(Optional.of(USER));

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, DEVOLUTION_DATE_OK);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = UserNotFoundException.class)
    public void borrowAssetFromUserNotPresentTest() throws Exception{
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(ASSET_INSTANCE));
        when(userDao.getUser(anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, DEVOLUTION_DATE_OK);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = AssetInstanceBorrowException.class)
    public void borrowAssetNotPublicTest() throws Exception{
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(ASSET_INSTANCE));
        when(userDao.getUser(anyString())).thenReturn(Optional.of(USER));

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, DEVOLUTION_DATE_OK);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = DayOutOfRangeException.class)
    public void borrowAssetInvalidDateTest() throws Exception{
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(ASSET_INSTANCE));
        when(userDao.getUser(anyString())).thenReturn(Optional.of(USER));

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, DEVOLUTION_DATE_WRONG);

        // 3 - Assertions
        Assert.fail();
    }


}
