package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

public interface AssetExistanceService {

    AssetInstanceImpl addAssetInstance(AssetInstanceImpl assetInstance, byte[] fileByteArray) throws InternalErrorException;

}
