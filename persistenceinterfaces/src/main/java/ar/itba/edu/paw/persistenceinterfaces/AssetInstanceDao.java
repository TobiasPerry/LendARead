package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;

import java.util.List;
import java.util.Optional;

public interface AssetInstanceDao {
    Optional<Integer> addAssetInstance(final int id,int ownerId,int locationId,int photoId,final AssetInstance ai);

    Optional<AssetInstance> getAssetInstance(final int assetId);
    Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage);

    Boolean changeStatus(int assetInstanceID, AssetState as);

    Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, List<String> authorsIn, List<String> languagesIn, List<String> physicalConditionsIn, String search);
}
