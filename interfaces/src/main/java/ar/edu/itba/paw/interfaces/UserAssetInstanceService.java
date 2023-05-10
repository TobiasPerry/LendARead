package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;

import java.util.List;

public interface UserAssetInstanceService {

    UserAssets getUserAssets(final String email, final String tableSelected, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction);

}
