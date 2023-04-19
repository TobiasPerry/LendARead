package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import java.util.Optional;

public interface AssetInstanceDao {
    Optional<Integer> addAssetInstance(final int id,int ownerId,int locationId,int photoId,final AssetInstance ai);

    Optional<AssetInstance> getAssetInstance(final int assetId);
    Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage);

    Boolean changeStatus(int assetInstanceID, AssetState as);
}
