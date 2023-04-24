package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.AvailableAsset;

public class AvailableAssetImpl implements AvailableAsset {

    AssetInstance assetInstance;

    AssetState assetState;


    @Override
    public boolean canBorrow(User user) {
        return assetState.isPublic() ; // assetInstance.getUser() != user
    }
}
