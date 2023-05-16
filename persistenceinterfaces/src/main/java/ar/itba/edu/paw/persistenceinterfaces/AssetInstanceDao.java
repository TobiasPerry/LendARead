package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.List;
import java.util.Optional;


public interface AssetInstanceDao {
    AssetInstance addAssetInstance(final Book book,final User owner,final Location location,final int photoId, final AssetInstance ai);

    Optional<AssetInstance> getAssetInstance(final int assetId);

    Boolean changeStatus(final int lendingId, final AssetState as);
    Boolean changeStatusByLendingId(final int lendingId, final AssetState as);


        Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery);
}
