package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

import java.util.List;
import java.util.Optional;

public interface UserAssetsDao {

    PageUserAssets getLendedAssets(final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    PageUserAssets getBorrowedAssets(final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    PageUserAssets getUsersAssets(final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    Optional<BorrowedAssetInstance> getBorrowedAsset(final int lendingId);
}
