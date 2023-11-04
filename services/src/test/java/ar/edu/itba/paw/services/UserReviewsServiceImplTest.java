package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
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
    UserAssetInstanceServiceImpl userAssetInstanceServiceImpl;
    @Mock
    UserServiceImpl userService;
    @InjectMocks
    UserReviewsServiceImpl userReviewsServiceImpl;

    private static final int USER_ID = 0;
    private static final int ASSET_ID = 0;
    private static final String EMAIL = "user@domain.com";
    private static final String EMAIL_DIFFERENT = "userother@domain.com";

    private static final String NAME = "John Doe";
    private static final String TELEPHONE = "";
    private static final String PASSWORD_ENCODED = "";
    private static final Behaviour BEHAVIOUR = Behaviour.BORROWER;
    private static final UserImpl USER = new UserImpl(USER_ID, EMAIL, NAME, TELEPHONE, PASSWORD_ENCODED, BEHAVIOUR);
    private static final UserImpl USER_DIFFERENT = new UserImpl(USER_ID + 1, EMAIL_DIFFERENT, NAME, TELEPHONE, PASSWORD_ENCODED, BEHAVIOUR);

    private static final AssetInstanceImpl ASSET_INSTANCE = new AssetInstanceImpl(
            new BookImpl(0, "", "", "", ""),
            PhysicalCondition.ASNEW,
            USER,
            new LocationImpl(0,"", "", "", "", "", null),
            new ImageImpl(),
            AssetState.PUBLIC,
            10,"DESC", false
    );
    private static final LendingImpl LENDING = new LendingImpl(ASSET_INSTANCE, USER, LocalDate.now(), LocalDate.now().plusDays(10), LendingState.FINISHED);
    private static final LendingImpl LENDING_NOT_FINISHED = new LendingImpl(ASSET_INSTANCE, USER, LocalDate.now(), LocalDate.now().plusDays(10), LendingState.ACTIVE);
    private static final UserReview USER_REVIEW = new UserReview("", 5, USER, USER, LENDING);

    @Test
    public void lenderCanReviewTrueTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceServiceImpl.getBorrowedAssetInstance(anyInt())).thenReturn(LENDING);
        when(userService.getCurrentUser()).thenReturn(USER.getEmail());
        when(userService.getUser(anyString())).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID);

        // 3 - Assertions
        Assert.assertTrue(returnValue);
    }

    @Test
    public void lenderCanReviewAlreadyReviewedFalseTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceServiceImpl.getBorrowedAssetInstance(anyInt())).thenReturn(LENDING);
        when(userService.getCurrentUser()).thenReturn(USER.getEmail());
//        when(userService.getUser(anyString())).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.of(USER_REVIEW));

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID);

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    @Test
    public void lenderCanReviewDifferentUserTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceServiceImpl.getBorrowedAssetInstance(anyInt())).thenReturn(LENDING);
        when(userService.getCurrentUser()).thenReturn(USER_DIFFERENT.getEmail());
        when(userService.getUser(anyString())).thenReturn(USER_DIFFERENT);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID);

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    @Test
    public void lenderCanReviewNotFinishedUserTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceServiceImpl.getBorrowedAssetInstance(anyInt())).thenReturn(LENDING_NOT_FINISHED);
        when(userService.getCurrentUser()).thenReturn(USER.getEmail());
        when(userService.getUser(anyString())).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.lenderCanReview(USER_ID);

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    //_____________________________________________________________________________________________

    @Test
    public void borrowerCanReviewTrueTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceServiceImpl.getBorrowedAssetInstance(anyInt())).thenReturn(LENDING);
        when(userService.getCurrentUser()).thenReturn(USER.getEmail());
        when(userService.getUser(anyString())).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.borrowerCanReview(USER_ID);

        // 3 - Assertions
        Assert.assertTrue(returnValue);
    }

    @Test
    public void borrowerCanReviewAlreadyReviewedFalseTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceServiceImpl.getBorrowedAssetInstance(anyInt())).thenReturn(LENDING);
        when(userService.getCurrentUser()).thenReturn(USER.getEmail());
        //when(userService.getUser(anyString())).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.of(USER_REVIEW));

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.borrowerCanReview(USER_ID);

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    @Test
    public void borrowerCanReviewDifferentUserTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceServiceImpl.getBorrowedAssetInstance(anyInt())).thenReturn(LENDING);
        when(userService.getCurrentUser()).thenReturn(USER_DIFFERENT.getEmail());
        when(userService.getUser(anyString())).thenReturn(USER_DIFFERENT);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.borrowerCanReview(USER_ID);

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }

    @Test
    public void borrowerCanReviewNotFinishedUserTest() throws UserNotFoundException, LendingNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceServiceImpl.getBorrowedAssetInstance(anyInt())).thenReturn(LENDING_NOT_FINISHED);
        when(userService.getCurrentUser()).thenReturn(USER.getEmail());
        when(userService.getUser(anyString())).thenReturn(USER);
        when(userReviewsDao.getUserReviewsByLendingIdAndUser(anyInt(), anyString())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        boolean returnValue = userReviewsServiceImpl.borrowerCanReview(USER_ID);

        // 3 - Assertions
        Assert.assertFalse(returnValue);
    }


}
