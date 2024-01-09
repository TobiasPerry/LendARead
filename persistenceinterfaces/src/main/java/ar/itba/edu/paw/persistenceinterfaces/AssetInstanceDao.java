package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.Optional;


public interface AssetInstanceDao {
     AssetInstance addAssetInstance(final AssetInstance ai);
     Optional<AssetInstance> getAssetInstance(final int assetId);

    void changeStatus(final AssetInstance ai, final AssetState as);
    void setReservability(final AssetInstance ai, final boolean value);


    Optional<AbstractPage<AssetInstance>> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery);
}
