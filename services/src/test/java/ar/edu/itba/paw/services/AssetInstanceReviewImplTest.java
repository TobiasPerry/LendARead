package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToAddReviewException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.assetLendingContext.LendingState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.Behaviour;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceReviewsDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssetInstanceReviewImplTest {

    @Mock
    private UserAssetInstanceService userAssetInstanceService;

    @Mock
    private UserService userService;

    @Mock
    private AssetInstanceReviewsDao assetInstanceReviewsDao;



    @InjectMocks
    private AssetInstanceReviewsServiceImpl assetInstanceReviewsService;
    @Before
    public void setUp() throws UserNotFoundException {
        when(userService.getCurrentUser()).thenReturn(new User( "EMAIL", "", "","", Behaviour.BORROWER,""));
    }
    @Test(expected = UnableToAddReviewException.class)
    public void addReviewLendingNotExistsTest() throws LendingNotFoundException, UserNotFoundException, UnableToAddReviewException {
        // 1 - Precondiciones
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.empty());

        // 2 - Ejercitación
       assetInstanceReviewsService.addReview(0, 0, "", 0);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = UnableToAddReviewException.class)
    public void addReviewLendingNotMatchAssetTest() throws  UserNotFoundException, UnableToAddReviewException {
        // 1 - Precondiciones
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(new Lending( new AssetInstance(1, new Asset(), PhysicalCondition.ASNEW, new User(), new Location(), new Image(), AssetState.DELETED,0, ""),new User(), LocalDate.now(),LocalDate.now(), LendingState.ACTIVE)));

        // 2 - Ejercitación
        assetInstanceReviewsService.addReview(0, 0, "", 0);

        // 3 - Assertions
        Assert.fail();
    }

    @Test
    public void canReviewTest() throws LendingNotFoundException, UserNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(new Lending( 0L,new AssetInstance(0, new Asset(), PhysicalCondition.ASNEW, new User(), new Location(), new Image(), AssetState.PRIVATE,0, ""),new User( "EMAIL", "", "","", Behaviour.BORROWER,""), LocalDate.now(),LocalDate.now(), LendingState.FINISHED)));
        // 2 - Ejercitación
        boolean canReview = assetInstanceReviewsService.canReview(0, 0);

        // 3 - Assertions
        Assert.assertTrue(canReview);
    }
    @Test
    public void canNotReviewTest() throws LendingNotFoundException, UserNotFoundException {
        // 1 - Precondiciones
        when(userAssetInstanceService.getBorrowedAssetInstance(anyInt())).thenReturn(Optional.of(new Lending( new AssetInstance(0, new Asset(), PhysicalCondition.ASNEW, new User(), new Location(), new Image(), AssetState.PRIVATE,0, ""),new User( "EMAIL", "", "","", Behaviour.BORROWER,""), LocalDate.now(),LocalDate.now(), LendingState.ACTIVE)));
        // 2 - Ejercitación
        boolean canReview = assetInstanceReviewsService.canReview(0, 0);

        // 3 - Assertions
        Assert.assertFalse(canReview);
    }

}
