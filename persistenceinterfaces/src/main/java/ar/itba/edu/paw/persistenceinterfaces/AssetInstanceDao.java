package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.Optional;


public interface AssetInstanceDao {
    public AssetInstanceImpl addAssetInstance(final BookImpl book, final UserImpl owner, final LocationImpl location, final int photoId, final AssetInstanceImpl ai);
    Optional<AssetInstanceImpl> getAssetInstance(final int assetId);

    Boolean changeStatus(final int lendingId, final AssetState as);

    Boolean changeStatusByLendingId(final int lendingId, final AssetState as);


    Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery);
}
