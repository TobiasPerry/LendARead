package ar.edu.itba.paw.services;

import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
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

    @Autowired
    private LocationDao locationDao;

    @Override
    public boolean addAssetInstance(AssetInstance assetInstance, byte[] photo) {
        Optional<Integer> assetId = bookDao.addAsset(assetInstance.getBook());
        Optional<Integer> userId = userDao.addUser(assetInstance.getOwner());
        Optional<Integer> locationId = locationDao.addLocation(assetInstance.getLocation());

        if(assetId.isPresent() && userId.isPresent() && locationId.isPresent()) {
            assetInstanceDao.addAssetInstance(assetId.get(), userId.get(),locationId.get(), assetInstance);
            return true;
        }
        return false;
    }
}
