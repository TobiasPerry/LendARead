package ar.edu.itba.paw.services;

import interfaces.AvailableAssetService;
import models.assetLendingContext.interfaces.AvailableAsset;
import models.userContext.interfaces.User;

public class AvailableAssetServiceImpl implements AvailableAssetService {
    @Override
    public boolean borrowAsset(User user, AvailableAsset availableAsset) {

        if(availableAsset.canBorrow(user)) {
            //do logic with persistence to borrow asset

           return true;
        }

        return false;
    }
}
