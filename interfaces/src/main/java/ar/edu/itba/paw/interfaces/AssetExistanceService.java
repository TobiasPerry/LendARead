package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

public interface AssetExistanceService {

    void addAssetInstance(AssetInstance assetInstance, byte[] fileByteArray) throws InternalErrorException;

}
