package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.*;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
final public class AssetExistanceServiceImpl implements AssetExistanceService {
    private final AssetDao bookDao;

    private final UserDao userDao;

    private final AssetInstanceDao assetInstanceDao;

    private final LocationDao locationDao;

    private final ImagesDao photosDao;

    @Autowired
    public AssetExistanceServiceImpl(AssetDao bookDao, UserDao userDao, AssetInstanceDao assetInstanceDao, LocationDao locationDao, ImagesDao photosDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.assetInstanceDao = assetInstanceDao;
        this.locationDao = locationDao;
        this.photosDao = photosDao;
    }

    @Transactional
    @Override
    public void addAssetInstance(AssetInstance assetInstance, byte[] photo) throws InternalErrorException{
        Optional<Integer> assetId = bookDao.addAsset(assetInstance.getBook());
        Optional<User> user = userDao.getUser(assetInstance.getOwner().getEmail());
        Optional<Integer> locationId = locationDao.addLocation(assetInstance.getLocation());
        Optional<Integer> photoId =  photosDao.addPhoto(photo);
        if(assetId.isPresent() && user.isPresent() && locationId.isPresent() && photoId.isPresent()) {
            assetInstanceDao.addAssetInstance(assetId.get(), user.get().getId(),locationId.get(),photoId.get(),assetInstance);
        }else {
            throw new InternalErrorException("Cannot addAssetInstance");
        }
    }
}
