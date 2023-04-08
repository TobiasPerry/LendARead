package ar.edu.itba.paw.services;

import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import interfaces.AssetExistanceService;
import models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
final public class AssetExistanceServiceImpl implements AssetExistanceService {
    @Autowired
    private AssetDao bookDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AssetInstanceDao assetInstanceDao;

    @Override
    public boolean addAssetInstance(AssetInstance assetInstance) {
        Optional<Integer> assetId = bookDao.addAsset(assetInstance.getBook());
        Optional<Integer> userId = userDao.addUser(assetInstance.getOwner());
        if(assetId.isPresent() && userId.isPresent())
            assetInstanceDao.addAssetInstance(assetId.get(),userId.get(),assetInstance);
        return true;
    }
}
