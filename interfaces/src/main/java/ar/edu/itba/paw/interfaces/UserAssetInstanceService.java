package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

public interface UserAssetInstanceService {

     PageUserAssets<AssetInstanceImpl> getUserAssetsInstances(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);
     PageUserAssets<LendingImpl> getUserBorrowedAssetsInstances(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction) ;
     PageUserAssets<LendingImpl> getUserLentAssetsInstances(final int pageNumber, final int itemsPerPage, final String email, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction) ;
     LendingImpl getBorrowedAssetInstance(final int lendingId) throws AssetInstanceNotFoundException;

}
