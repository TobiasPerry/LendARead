package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.implementations.UserAssetsImpl;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAssetInstanceServiceImpl implements UserAssetInstanceService {

    private final UserAssetsDao   userAssetsDao;


    @Autowired
    public UserAssetInstanceServiceImpl(UserAssetsDao userAssetsDao) {
        this.userAssetsDao = userAssetsDao;
    }

    @Override
    public UserAssets getUserAssets(String email) {
        return new UserAssetsImpl(userAssetsDao.getLendedAssets(email), userAssetsDao.getBorrowedAssets(email), userAssetsDao.getUsersAssets(email));
    }


    @Override
    public List<BorrowedAssetInstance> getUserLendedAssetsFilteredBy(String email, String atribuite) {
        return userAssetsDao.getLendedAssetsFilteredBy(email, atribuite);
    }

}
