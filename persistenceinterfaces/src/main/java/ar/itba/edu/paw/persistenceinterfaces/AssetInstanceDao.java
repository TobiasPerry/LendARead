package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.Optional;


public interface AssetInstanceDao {
     AssetInstance addAssetInstance(final AssetInstance ai);
     Optional<AssetInstance> getAssetInstance(final int assetId);

    void changeStatus(final AssetInstance ai, final AssetState as);
    void setReservability(final AssetInstance ai, final boolean value);

    void changeStatusByLendingId(AssetInstance ai, final AssetState as);

    void changePhysicalCondition(final AssetInstance ai, final PhysicalCondition physicalCondition);
    void changeLocation(final AssetInstance ai, final Location location);
    void changeMaxLendingDays(final AssetInstance ai, final int maxLendingDays);
    void changeImage(final AssetInstance ai, final Image image);
    Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery);
}
