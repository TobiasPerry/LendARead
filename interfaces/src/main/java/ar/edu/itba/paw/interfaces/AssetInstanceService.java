package ar.edu.itba.paw.interfaces;
//import models.assetExistanceContext.interfaces.AssetInstance;
//import models.assetExistanceContext.interfaces.Book;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.Optional;

public interface AssetInstanceService {
    Optional<AssetInstance> getAssetInstance(final int id) throws AssetInstanceNotFoundException;


    AbstractPage<AssetInstance> getAllAssetsInstances(final int pageNum, final int itemPerPage, final SearchQuery searchQuery);

    void removeAssetInstance(final int id) throws AssetInstanceNotFoundException, UnableToDeleteAssetInstanceException;

    boolean isOwner(final int id, final String email) throws AssetInstanceNotFoundException;

    void changeAssetInstance(final int id, final Optional<PhysicalCondition> physicalCondition, final Optional<Integer> maxLendingDays, final Optional<Integer> location, final Optional<Integer> imageId, final Optional<String> description,final Optional<Boolean> isReservable,final Optional<String> state) throws AssetInstanceNotFoundException, LocationNotExistException, ImageNotExistException, UserNotFoundException, UserIsNotOwnerException, UnableToChangeAssetStateException, UnableToChangeAssetReservavilityException;
     AssetInstance addAssetInstance(final PhysicalCondition physicalCondition, final String description, final int maxDays, final Boolean isReservable, final AssetState assetState, final int locationId, final Long assetId,final int imageId) throws UserNotFoundException, LocationNotExistException, AssetNotExistException, ImageNotExistException, UserIsNotOwnerException;


    }
