package ar.edu.itba.paw.services;

import ar.itba.edu.paw.persistenceinterfaces.BookInstanceDao;
import interfaces.AssetExistanceService;
import models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
final public class AssetExistanceServiceImpl implements AssetExistanceService {
    @Autowired
    private BookInstanceDao bookDao;

    @Override
    public boolean addAssetInstance(AssetInstance assetInstance) {
        bookDao.addAsset(assetInstance.getBook());

        System.out.println(assetInstance.toString());
        return true;
    }
}
