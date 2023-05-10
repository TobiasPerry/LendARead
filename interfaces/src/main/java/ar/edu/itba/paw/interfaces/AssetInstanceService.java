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
    AssetInstance getAssetInstance(int id) throws AssetInstanceNotFoundException;

    Page getAllAssetsInstances(int pageNum,int itemsPerPage);

    List<AssetInstance> getAllAssetsInstances();
    Page getAllAssetsInstances(int pageNum, int itemPerPage, SearchQuery searchQuery);
    void removeAssetInstance(int id) throws AssetInstanceNotFoundException;
    boolean isOwner(int id, String email) throws AssetInstanceNotFoundException;
}
