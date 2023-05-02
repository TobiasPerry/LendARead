package ar.edu.itba.paw.interfaces;
//import models.assetExistanceContext.interfaces.AssetInstance;
//import models.assetExistanceContext.interfaces.Book;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface AssetInstanceService {
    Optional<AssetInstance> getAssetInstance(int id);

     Page getAllAssetsInstances(int pageNum);

     List<AssetInstance> getAllAssetsInstances();
     Page getAllAssetsInstances(int pageNum, SearchQuery searchQuery);

    boolean removeAssetInstance(int id);

    boolean isOwner(int id,String email);
}
