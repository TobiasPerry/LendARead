package models.assetLendingContext.implementations;

import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetLendingContext.interfaces.AvailableAsset;
import models.userContext.interfaces.User;

public class AvailableAssetImpl implements AvailableAsset {

    AssetInstance assetInstance;

    AssetState assetState;


    @Override
    public boolean canBorrow(User user) {
        return assetState.canBorrow() ; // assetInstance.getUser() != user
    }
}
