package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;

import java.util.List;

public interface UserAssetInstanceService {

    List<? extends AssetInstance> getUserAssetsOfTable(final String email, final String tableSelected, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

}
