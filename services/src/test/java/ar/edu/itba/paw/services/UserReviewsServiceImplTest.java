package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
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
import ar.edu.itba.paw.models.userContext.UserReview;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserReviewsServiceImplTest {

    @Mock
    UserReviewsDao userReviewsDao;

    @Mock
    UserServiceImpl userService;

    @Mock
    UserAssetInstanceService userAssetInstanceService;

    @InjectMocks
    UserReviewsServiceImpl userReviewsServiceImpl;



    private static final int USER_ID = 0;
    private static final int ASSET_ID = 0;
    private static final String EMAIL = "user@domain.com";
    private static final String EMAIL_DIFFERENT = "userother@domain.com";

    private static final String NAME = "John Doe";

    private static final String LOCALE = "LOCALE";
    private static final String TELEPHONE = "";
    private static final String PASSWORD_ENCODED = "";
    private static final Behaviour BEHAVIOUR = Behaviour.BORROWER;
    private static final User USER = new User(USER_ID, EMAIL, NAME, TELEPHONE, PASSWORD_ENCODED, BEHAVIOUR, LOCALE);
    private static final User USER_DIFFERENT = new User(USER_ID + 1, EMAIL_DIFFERENT, NAME, TELEPHONE, PASSWORD_ENCODED, BEHAVIOUR, LOCALE);

    private static final AssetInstance ASSET_INSTANCE = new AssetInstance(
            new Asset((long)0, "", "", "", new Language()),
            PhysicalCondition.ASNEW,
            USER,
            new Location(0,"", "", "", "", "", null),
            new Image(),
            AssetState.PUBLIC,
            10,"DESC", false
    );
    private static final Lending LENDING = new Lending(0L,ASSET_INSTANCE, USER, LocalDate.now(), LocalDate.now().plusDays(10), LendingState.FINISHED);
    private static final Lending LENDING_NOT_FINISHED = new Lending(1L,ASSET_INSTANCE, USER, LocalDate.now(), LocalDate.now().plusDays(10), LendingState.ACTIVE);
    private static final UserReview USER_REVIEW = new UserReview("", 5, USER, USER, LENDING);

    @Test
    public void lenderCanReviewTrueTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userService.getCurrentUser()).thenReturn(new User(USER_ID, EMAIL, NAME, TELEPHONE, PASSWORD_ENCODED, Behaviour.LENDER, LOCALE));
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(LENDING));

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID, Math.toIntExact(LENDING.getId()));

        // 3 - Assertions
        Assert.assertTrue(returnValue);
    }

    @Test
    public void lenderCanReviewAlreadyReviewedFalseTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userService.getCurrentUser()).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.of(USER_REVIEW));
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(LENDING));

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID, Math.toIntExact(LENDING.getId()));

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    @Test
    public void lenderCanReviewDifferentUserTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userService.getCurrentUser()).thenReturn(USER_DIFFERENT);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(LENDING));


        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID, Math.toIntExact(LENDING.getId()));

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    @Test
    public void lenderCanReviewNotFinishedUserTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userService.getCurrentUser()).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(LENDING_NOT_FINISHED));


        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID, Math.toIntExact(LENDING_NOT_FINISHED.getId()));

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    //_____________________________________________________________________________________________

    @Test
    public void borrowerCanReviewTrueTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userService.getCurrentUser()).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(LENDING));


        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID, Math.toIntExact(LENDING.getId()));

        // 3 - Assertions
        Assert.assertTrue(returnValue);
    }

    @Test
    public void borrowerCanReviewAlreadyReviewedFalseTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userService.getCurrentUser()).thenReturn(USER);
        //when(userService.getUser(anyString())).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.of(USER_REVIEW));
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(LENDING));


        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID, Math.toIntExact(LENDING.getId()));

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    @Test
    public void borrowerCanReviewDifferentUserTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userService.getCurrentUser()).thenReturn(USER_DIFFERENT);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(LENDING));

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.borrowerCanReview(USER_ID, Math.toIntExact(LENDING.getId()));

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    @Test
    public void borrowerCanReviewNotFinishedUserTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userService.getCurrentUser()).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(LENDING_NOT_FINISHED));


        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.borrowerCanReview(USER_ID, Math.toIntExact(LENDING_NOT_FINISHED.getId()));

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }


}
