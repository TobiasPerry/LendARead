package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

import java.util.Optional;

public interface UserAssetsDao {

    PageUserAssets<Lending> getLendedAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    PageUserAssets<Lending> getBorrowedAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    PageUserAssets<AssetInstance> getUsersAssets(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    Optional<Lending> getBorrowedAsset(final int lendingId);
}
