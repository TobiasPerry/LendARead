package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.interfaces.AssetService;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
final public class AssetExistanceServiceImpl implements AssetExistanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    private final AssetService assetService;

    private final UserService userService;

    private final AssetInstanceDao assetInstanceDao;

    private final LocationsService locationsService;
    private final ImagesDao photosDao;

    @Autowired
    public AssetExistanceServiceImpl(AssetService assetService, AssetInstanceDao assetInstanceDao, LocationsService locationsService, ImagesDao photosDao, UserService userService) {
        this.assetService = assetService;
        this.assetInstanceDao = assetInstanceDao;
        this.locationsService = locationsService;
        this.photosDao = photosDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    public AssetInstance addAssetInstance(final PhysicalCondition physicalCondition, final String description, final int maxDays, final Boolean isReservable, final AssetState assetState, final int locationId, final Long assetId, byte[] fileByteArray) throws InternalErrorException, UserNotFoundException, LocationNotFoundException {

        Asset book = assetService.getBookById(assetId);
        User user = userService.getUser(userService.getCurrentUser());
        Location location = locationsService.getLocation(locationId);
        Image image = photosDao.addPhoto(fileByteArray);
        AssetInstance assetInstance = new AssetInstance();
        assetInstance.setBook(book);
        assetInstance.setLocation(location);
        assetInstance.setImage(image);
        assetInstance.setUserReference(user);
        assetInstance.setPhysicalCondition(physicalCondition);
        assetInstance.setDescription(description);
        assetInstance.setMaxLendingDays(maxDays);
        assetInstance.setReservable(isReservable);
        assetInstance.setAssetState(assetState);
        return assetInstanceDao.addAssetInstance(assetInstance);
    }
}
