package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.Language;
import ar.edu.itba.paw.models.assetExistanceContext.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.assetLendingContext.LendingState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.Behaviour;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

    @RunWith(MockitoJUnitRunner.class)
public class LendingServiceImplTest {

    @Mock
    private UserDao userDao;
    @Mock
    private AssetInstanceDao assetInstanceDao;
    @Mock
    private AssetAvailabilityDao lendingDao;

    @InjectMocks
    private LendingServiceImpl assetAvailabilityService;

    private static final int USER_ID = 0;
    private static final int ASSET_ID = 0;
    private static final String EMAIL = "user@domain.com";
    private static final String NAME = "John Doe";
    private static final String TELEPHONE = "";
    private static final String LOCALE = "LOCALE";
    private static final String PASSWORD_ENCODED = "";
    private static final Behaviour BEHAVIOUR = Behaviour.BORROWER;
    private static final LocalDate BORROW_DATE_TODAY = LocalDate.now();
    private static final LocalDate BORROW_DATE_FUTURE = LocalDate.now().plusDays(1);
    private static final LocalDate DEVOLUTION_DATE_OK = LocalDate.now().plusDays(5);
    private static final LocalDate DEVOLUTION_DATE_WRONG = LocalDate.now().plusDays(30);

    private static final LocalDate BORROW_DATE_THREE_WEEK = LocalDate.now().plusDays(21);
    private static final LocalDate DEVOLUTION_DATE_ONE_WEEK = LocalDate.now().plusDays(7);
    private static final LocalDate DEVOLUTION_DATE_FOUR_WEEK = LocalDate.now().plusDays(28);
    private static final User USER = new User(USER_ID, EMAIL, NAME, TELEPHONE, PASSWORD_ENCODED, BEHAVIOUR, LOCALE);
    private static final AssetInstance ASSET_INSTANCE = new AssetInstance(
            new Asset((long)0, "", "", "", new Language()),
            PhysicalCondition.ASNEW,
            USER,
            new Location(0,"", "", "", "", "", null),
            new Image(),
            AssetState.PUBLIC,
            10,"DESC", false
    );

    private static final List<Lending> LENDING_LIST = new ArrayList<>(Arrays.asList(
            new Lending(ASSET_INSTANCE, USER, BORROW_DATE_TODAY, DEVOLUTION_DATE_ONE_WEEK, LendingState.ACTIVE),
            new Lending(ASSET_INSTANCE, USER, BORROW_DATE_THREE_WEEK, DEVOLUTION_DATE_FOUR_WEEK, LendingState.ACTIVE)
    )
    );

    @Test(expected = AssetInstanceBorrowException.class)
    public void borrowAssetNotPresentTest() throws Exception {
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.empty());
        when(userDao.getUser(anyString())).thenReturn(Optional.of(USER));

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL,DEVOLUTION_DATE_OK ,DEVOLUTION_DATE_OK);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = AssetIsNotAvailableException.class)
    public void borrowAssetFromUserNotPresentTest() throws Exception {
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(ASSET_INSTANCE));
        when(userDao.getUser(anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, DEVOLUTION_DATE_OK,DEVOLUTION_DATE_OK);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = AssetInstanceIsNotReservableException.class)
    public void borrowAssetNotPublicTest() throws Exception {
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(ASSET_INSTANCE));
        when(userDao.getUser(anyString())).thenReturn(Optional.of(USER));

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, BORROW_DATE_FUTURE, DEVOLUTION_DATE_OK);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = MaxLendingDaysException.class)
    public void borrowAssetInvalidDateTest() throws Exception {
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(ASSET_INSTANCE));
        when(userDao.getUser(anyString())).thenReturn(Optional.of(USER));

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, BORROW_DATE_TODAY, DEVOLUTION_DATE_WRONG);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = AssetInstanceIsNotReservableException.class)
    public void borrowAssetNotReservable() throws UserNotFoundException, AssetInstanceBorrowException, DayOutOfRangeException, MaxLendingDaysException, AssetIsNotAvailableException, AssetInstanceIsNotReservableException {
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(ASSET_INSTANCE));
        when(userDao.getUser(anyString())).thenReturn(Optional.of(USER));

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, BORROW_DATE_FUTURE, DEVOLUTION_DATE_OK);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = AssetInstanceIsNotReservableException.class)
    public void borrowAssetOverlappingDates() throws UserNotFoundException, AssetInstanceBorrowException, DayOutOfRangeException, MaxLendingDaysException, AssetIsNotAvailableException, AssetInstanceIsNotReservableException {
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(ASSET_INSTANCE));
        when(userDao.getUser(anyString())).thenReturn(Optional.of(USER));

        // 2 - Ejercitación
        assetAvailabilityService.borrowAsset(ASSET_ID, EMAIL, BORROW_DATE_THREE_WEEK, DEVOLUTION_DATE_FOUR_WEEK);

        // 3 - Assertions
        Assert.fail();
    }

}
