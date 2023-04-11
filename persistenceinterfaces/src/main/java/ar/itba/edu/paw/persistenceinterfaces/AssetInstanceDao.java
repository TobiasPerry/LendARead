package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

import java.util.Optional;

public interface AssetInstanceDao {
    Optional<Integer> addAssetInstance(final int id,int ownerId,int locationId,int photoId,final AssetInstance ai);

}
