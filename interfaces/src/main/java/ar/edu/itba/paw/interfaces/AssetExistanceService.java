package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;

public interface AssetExistanceService {

    AssetInstanceImpl addAssetInstance(AssetInstanceImpl assetInstance, byte[] fileByteArray) throws InternalErrorException;

}
