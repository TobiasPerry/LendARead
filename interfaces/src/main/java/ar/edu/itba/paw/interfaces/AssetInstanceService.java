package ar.edu.itba.paw.interfaces;
//import models.assetExistanceContext.interfaces.AssetInstance;
//import models.assetExistanceContext.interfaces.Book;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.Optional;

public interface AssetInstanceService {
    AssetInstanceImpl getAssetInstance(final int id) throws AssetInstanceNotFoundException;

    Page getAllAssetsInstances(final int pageNum, int itemsPerPage);

    Page getAllAssetsInstances(final int pageNum, final int itemPerPage, final SearchQuery searchQuery);

    void removeAssetInstance(final int id) throws AssetInstanceNotFoundException;

    boolean isOwner(final int id, final String email) throws AssetInstanceNotFoundException;

    boolean isOwner(final AssetInstanceImpl assetInstance, final String email);

    void changeAssetInstance(final int id, final Optional<PhysicalCondition> physicalCondition, final Optional<Integer> maxLendingDays, final Optional<LocationImpl> location, final Optional<byte[]> photo) throws AssetInstanceNotFoundException;

    ;
}
