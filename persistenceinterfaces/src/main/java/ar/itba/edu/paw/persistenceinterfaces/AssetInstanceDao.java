package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.Optional;


public interface AssetInstanceDao {
     AssetInstanceImpl addAssetInstance(final AssetInstanceImpl ai);
     Optional<AssetInstanceImpl> getAssetInstance(final int assetId);

    void changeStatus(final AssetInstanceImpl ai,final AssetState as);

    void changeStatusByLendingId(AssetInstanceImpl ai, final AssetState as);


    Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery);
}
