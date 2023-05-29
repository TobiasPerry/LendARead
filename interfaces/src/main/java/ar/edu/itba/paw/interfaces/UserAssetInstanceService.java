package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

public interface UserAssetInstanceService {

    PageUserAssets getUserAssetsOfTable(final int pageNumber, final int itemsPerPage, final String email, final String tableSelected, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

    LendingImpl getBorrowedAssetInstance(final int lendingId) throws AssetInstanceNotFoundException;

}
