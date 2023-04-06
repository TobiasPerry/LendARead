package ar.edu.itba.paw.services;

import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetLendingContext.interfaces.AvailableAsset;
import models.userContext.interfaces.User;

public interface AvailableAssetService {

    boolean borrowAsset(User user, AvailableAsset assetInstance);

}
