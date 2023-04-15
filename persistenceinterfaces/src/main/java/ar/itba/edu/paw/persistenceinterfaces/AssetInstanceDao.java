package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;

import java.util.List;
import java.util.Optional;

public interface AssetInstanceDao {
    Optional<Integer> addAssetInstance(final int id,int ownerId,int locationId,int photoId,final AssetInstance ai);

    Optional<AssetInstance> getAssetInstance(final int assetId);
    Optional<List<AssetInstance>> getAllAssetInstances();

    Boolean changeStatus(int assetInstanceID, AssetState as);
}
