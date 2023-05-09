package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;

import java.util.List;

public interface UserAssetInstanceService {

    UserAssets getUserAssets(String email);
    List<BorrowedAssetInstance> getUserLendedAssetsFilteredBy(String email, String attribuite);

}
