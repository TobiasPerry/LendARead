package ar.edu.itba.paw.interfaces;
//import models.assetExistanceContext.interfaces.AssetInstance;
//import models.assetExistanceContext.interfaces.Book;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface AssetInstanceService {
    AssetInstance getAssetInstance(final int id) throws AssetInstanceNotFoundException;

    Page getAllAssetsInstances(final int pageNum,int itemsPerPage);

    Page getAllAssetsInstances(final int pageNum,final int itemPerPage,final SearchQuery searchQuery);
    void removeAssetInstance(final int id) throws AssetInstanceNotFoundException;
    boolean isOwner(final int id,final String email) throws AssetInstanceNotFoundException;
    boolean isOwner(final AssetInstance assetInstance,final String email);
}
