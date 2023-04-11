package ar.itba.edu.paw.persistenceinterfaces;

import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetExistanceContext.interfaces.Book;

import java.util.List;
import java.util.Optional;

public interface AssetInstanceDao {
    Optional<Integer> addAssetInstance(final int id,int ownerId,int locationId,final AssetInstance ai);

    Optional<List<AssetInstance>> getAllAssetInstances();
}
