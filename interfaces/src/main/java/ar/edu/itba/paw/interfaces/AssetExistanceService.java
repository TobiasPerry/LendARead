package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

public interface AssetExistanceService {

    boolean addAssetInstance(AssetInstance assetInstance, byte[] fileByteArray);

}
