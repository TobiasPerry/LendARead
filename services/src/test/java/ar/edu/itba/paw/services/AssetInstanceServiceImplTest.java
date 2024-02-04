package ar.edu.itba.paw.services;


import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToDeleteAssetInstanceException;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssetInstanceServiceImplTest {

    @Mock
    private AssetInstanceDao assetInstanceDao;

    @InjectMocks
    private AssetInstanceServiceImpl assetInstanceService;

    private static final int PAGE_NAME = 1;
    private static final int PAGE_NAME_INVALID = -1;
    private static final int ITEMS_PER_PAGE = 15;
    private static final int ITEMS_PER_PAGE_INVALID = 0;
    private static final SearchQuery SEARCH_QUERY = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "", 1, 5,-1, AssetState.PUBLIC);
    private static final AssetInstance DELETED_ASSET_INSTANCE = new AssetInstance(0, new Asset(), PhysicalCondition.ASNEW, new User(), new Location(), new Image(), AssetState.DELETED,0, "");

    @Test
    public void getAllAssetsInstancesEmptyDBTest(){
        // 1 - Precondiciones
        when(assetInstanceDao.getAllAssetInstances(anyInt(), anyInt(), any())).thenReturn(new PagingImpl<>(new ArrayList<>(), 1, 1));

        // 2 - Ejercitación
        AbstractPage<AssetInstance> page = assetInstanceService.getAllAssetsInstances(PAGE_NAME, ITEMS_PER_PAGE, SEARCH_QUERY);

        // 3 - Assertions
        Assert.assertNotNull(page);
        Assert.assertEquals(page.getCurrentPage(), 1);
        Assert.assertEquals(page.getTotalPages(), 1);

    }

    @Test
    public void getAllAssetsInstancesInvalidPageNumTest(){
        // 1 - Precondiciones

        // 2 - Ejercitación
        AbstractPage<AssetInstance> page = assetInstanceService.getAllAssetsInstances(PAGE_NAME_INVALID, ITEMS_PER_PAGE, SEARCH_QUERY);

        // 3 - Assertions
        Assert.assertNotNull(page);
        Assert.assertEquals(page.getCurrentPage(), 1);
        Assert.assertEquals(page.getTotalPages(), 1);

    }

    @Test
    public void getAllAssetsInstancesInvalidItemsPerPageTest(){
        // 1 - Precondiciones

        // 2 - Ejercitación
        AbstractPage<AssetInstance> page = assetInstanceService.getAllAssetsInstances(PAGE_NAME, ITEMS_PER_PAGE_INVALID, SEARCH_QUERY);

        // 3 - Assertions
        Assert.assertNotNull(page);
        Assert.assertEquals(page.getCurrentPage(), 1);
        Assert.assertEquals(page.getTotalPages(), 1);
    }
    @Test(expected = AssetInstanceNotFoundException.class)
    public void assetInstanceNotFound() throws AssetInstanceNotFoundException {
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.empty());

        // 2 - Ejercitación

        assetInstanceService.getAssetInstance(1).orElseThrow(AssetInstanceNotFoundException::new);

        // 3 - Assertions
        Assert.fail();
    }

    @Test(expected = UnableToDeleteAssetInstanceException.class)
    public void removeAssetInstanceAlreadyDeleted() throws AssetInstanceNotFoundException, UnableToDeleteAssetInstanceException {
        // 1 - Precondiciones
        when(assetInstanceDao.getAssetInstance(anyInt())).thenReturn(Optional.of(DELETED_ASSET_INSTANCE));

        // 2 - Ejercitación
        assetInstanceService.removeAssetInstance(1);

        // 3 - Assertions
        Assert.fail();
    }


}
