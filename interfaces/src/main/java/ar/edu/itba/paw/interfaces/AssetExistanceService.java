package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

public interface AssetExistanceService {

    AssetInstance addAssetInstance(AssetInstance assetInstance, byte[] fileByteArray) throws InternalErrorException;

}
