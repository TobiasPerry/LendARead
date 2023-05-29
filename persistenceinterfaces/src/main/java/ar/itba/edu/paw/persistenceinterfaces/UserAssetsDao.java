package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

import java.util.Optional;

public interface UserAssetsDao {

    PageUserAssets<LendingImpl> getLendedAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    PageUserAssets<LendingImpl> getBorrowedAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    PageUserAssets<AssetInstanceImpl> getUsersAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    Optional<LendingImpl> getBorrowedAsset(final int lendingId);
}
