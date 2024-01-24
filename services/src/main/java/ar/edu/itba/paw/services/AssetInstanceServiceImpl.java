package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);


    private final AssetInstanceDao assetInstanceDao;


    private final ImageService imageService;
    private final LocationsService locationsService;

    private final UserService userService;

    private final AssetService assetService;



    @Autowired
    public AssetInstanceServiceImpl( final AssetInstanceDao assetInstanceDao, final ImagesDao imagesDao,final LocationsService locationsService,final UserService userService,final AssetService assetService,final ImageService imageService) {
        this.assetInstanceDao = assetInstanceDao;
        this.imageService = imageService;
        this.locationsService = locationsService;
        this.userService = userService;
        this.assetService = assetService;
    }

    @Transactional(readOnly = true)
    @Override
    public AssetInstance getAssetInstance(final int id) throws AssetInstanceNotFoundException {
        Optional<AssetInstance> assetInstanceOpt = this.assetInstanceDao.getAssetInstance(id);
        if (!assetInstanceOpt.isPresent()) {
            LOGGER.info("Failed to find the asset instance");
            throw new AssetInstanceNotFoundException();
        }
        return assetInstanceOpt.get();
    }


    @Transactional(readOnly = true)
    @Override
    public AbstractPage<AssetInstance> getAllAssetsInstances(final int pageNum, final int itemsPerPage, SearchQuery searchQuery) {

        if (pageNum < 0 || itemsPerPage <= 0)
            return new PagingImpl<>(Collections.emptyList(), 1, 1);

        if (searchQuery == null)
            searchQuery = new SearchQueryImpl(Collections.emptyList(), Collections.emptyList(), "", 1, 5,-1,AssetState.PUBLIC);


        return assetInstanceDao.getAllAssetInstances(pageNum, itemsPerPage, searchQuery);
    }

    @Transactional
    @Override
    public void removeAssetInstance(final int id) throws AssetInstanceNotFoundException, UnableToDeleteAssetInstanceException {
        AssetInstance assetInstance = getAssetInstance(id);
        if (assetInstance.getAssetState() == AssetState.DELETED) {
            LOGGER.info("Asset instance already deleted");
            throw new UnableToDeleteAssetInstanceException();
        }
        assetInstanceDao.changeStatus(assetInstance,AssetState.DELETED);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isOwner(final int id, final String email) throws AssetInstanceNotFoundException {
        AssetInstance assetInstance = getAssetInstance(id);
        return assetInstance.getOwner().getEmail().equals(email);
    }
    @Transactional
    @Override
    public void changeAssetInstance(final int id, final Optional<PhysicalCondition> physicalCondition, final Optional<Integer> maxLendingDays, final Optional<Integer> location,final Optional<Integer> imageId,final Optional<String> description,final Optional<Boolean> isReservable,final Optional<String> state) throws AssetInstanceNotFoundException, LocationNotExistException, ImageNotExistException, UserNotFoundException, UserIsNotOwnerException {
        AssetInstance assetInstance = getAssetInstance(id);
        User user = userService.getCurrentUser();

        if (location.isPresent()) {
            Location loc;
            try {
                loc = locationsService.getLocation(location.get());
                if (!loc.isActive())
                    throw new LocationNotExistException();
                if (!loc.getUser().getEmail().equals(user.getEmail())) throw new UserIsNotOwnerException();

            } catch (LocationNotFoundException e) {
                throw new LocationNotExistException();
            }
            assetInstance.setLocation(loc);
        }
        if (imageId.isPresent()) {
            Image image1;
            try {
                image1 = imageService.getImage(imageId.get());
            }catch (ImageNotFoundException e) {
                throw new ImageNotExistException();
            }
            assetInstance.setImage(image1);
        }
        description.ifPresent(assetInstance::setDescription);
        physicalCondition.ifPresent(assetInstance::setPhysicalCondition);
        maxLendingDays.ifPresent(assetInstance::setMaxLendingDays);
        isReservable.ifPresent(assetInstance::setIsReservable);
        state.ifPresent(s -> assetInstance.setAssetState(AssetState.fromString(s)));

    }
    @Override
    @Transactional
    public AssetInstance addAssetInstance(final PhysicalCondition physicalCondition, final String description, final int maxDays, final Boolean isReservable, final AssetState assetState, final int locationId, final Long assetId, final int imageId) throws UserNotFoundException, LocationNotExistException, AssetNotExistException, ImageNotExistException, UserIsNotOwnerException {
        Asset book ;
        Location location;
        User user = userService.getCurrentUser();
        try {
            book = assetService.getBookById(assetId);
            location =   locationsService.getLocation(locationId);
            if (!location.isActive()) throw new LocationNotExistException();
            if (!location.getUser().getEmail().equals(user.getEmail())) throw new UserIsNotOwnerException();
        }
        catch (AssetNotFoundException e) {
            throw new AssetNotExistException();
        }
        catch (LocationNotFoundException e) {
            throw new LocationNotExistException();
        }
        Image image ;
        try {
            image = imageService.getImage(imageId);
        }
        catch (ImageNotFoundException e) {
            throw new ImageNotExistException();
        }
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
