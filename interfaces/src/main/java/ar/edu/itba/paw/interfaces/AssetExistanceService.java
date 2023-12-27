package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;

public interface AssetExistanceService {

    AssetInstance addAssetInstance(final PhysicalCondition physicalCondition, final String description, final int maxDays, final Boolean isReservable, final AssetState assetState, final int locationId, final Long assetId, byte[] fileByteArray) throws InternalErrorException, UserNotFoundException, LocationNotFoundException;

}
