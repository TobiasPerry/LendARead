package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

public interface UserAssetInstanceService {

     PageUserAssets<AssetInstance> getUserAssetsInstances(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);
     PageUserAssets<Lending> getUserBorrowedAssetsInstances(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction) ;
     PageUserAssets<Lending> getUserLentAssetsInstances(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction) ;
     Lending getBorrowedAssetInstance(final int lendingId) throws LendingNotFoundException;

}
