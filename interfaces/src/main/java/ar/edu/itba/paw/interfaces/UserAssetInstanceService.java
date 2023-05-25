package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.models.assetLendingContext.implementations.BorrowedAssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

public interface UserAssetInstanceService {

    PageUserAssets getUserAssetsOfTable(final int pageNumber, final int itemsPerPage, final String email, final String tableSelected, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    BorrowedAssetInstanceImpl getBorrowedAssetInstance(final int lendingId) throws AssetInstanceNotFoundException;

}
