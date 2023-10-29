package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    private final AssetDao assetDao;

    private final AssetInstanceDao assetInstanceDao;


    private final ImagesDao imagesDao;

    private final LocationsService locationsService;

    private final ImageService imageService;

    @Autowired
    public AssetInstanceServiceImpl(final AssetDao assetDao, final AssetInstanceDao assetInstanceDao, final ImagesDao imagesDao,final LocationsService locationsService,final ImageService imageService) {
        this.assetDao = assetDao;
        this.assetInstanceDao = assetInstanceDao;
        this.imagesDao = imagesDao;
        this.locationsService = locationsService;
        this.imageService = imageService;
    }

    @Transactional(readOnly = true)
    @Override
    public AssetInstanceImpl getAssetInstance(final int id) throws AssetInstanceNotFoundException {
        Optional<AssetInstanceImpl> assetInstanceOpt = this.assetInstanceDao.getAssetInstance(id);
        if (!assetInstanceOpt.isPresent()) {
            LOGGER.error("Failed to find the asset instance");
            throw new AssetInstanceNotFoundException("assetInstance not found");
        }
        return assetInstanceOpt.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllAssetsInstances(final int pageNum, final int itemsPerPage) {
        return getAllAssetsInstances(pageNum, itemsPerPage, new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "", 1, 5));
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllAssetsInstances(final int pageNum, final int itemsPerPage, SearchQuery searchQuery) {

        if (pageNum < 0 || itemsPerPage <= 0)
            return new PageImpl(new ArrayList<>(), 1, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        if (searchQuery == null)
            searchQuery = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "", 1, 5);


        Optional<Page> optionalPage = assetInstanceDao.getAllAssetInstances(pageNum, itemsPerPage, searchQuery);

        Page page;

        if (optionalPage.isPresent()) {
            page = optionalPage.get();
            return page;
        }
        return new PageImpl(new ArrayList<>(), 1, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Transactional
    @Override
    public void removeAssetInstance(final int id) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = getAssetInstance(id);
        assetInstanceDao.changeStatus(assetInstance,AssetState.DELETED);
    }

    @Override
    public boolean isOwner(final AssetInstanceImpl assetInstance, final String email) {
        return assetInstance.getOwner().getEmail().equals(email);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isOwner(final int id, final String email) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = getAssetInstance(id);
        return assetInstance.getOwner().getEmail().equals(email);
    }
    @Transactional
    @Override
    public void changeAssetInstance(final int id, final Optional<PhysicalCondition> physicalCondition, final Optional<Integer> maxLendingDays, final Optional<Integer> location,final byte[] image,final Optional<String> description,final Optional<Boolean> isReservable) throws AssetInstanceNotFoundException, LocationNotFoundException, ImageNotFoundException {
        AssetInstanceImpl assetInstance = getAssetInstance(id);
        if (location.isPresent())
            assetInstance.setLocation(locationsService.getLocation(location.get()));
        if (image != null)
            assetInstance.setImage(imagesDao.addPhoto(image));
        description.ifPresent(assetInstance::setDescription);
        physicalCondition.ifPresent(assetInstance::setPhysicalCondition);
        maxLendingDays.ifPresent(assetInstance::setMaxLendingDays);
        isReservable.ifPresent(assetInstance::setIsReservable);

    }

    @Transactional(readOnly = true)
    @Override
    public List<AssetInstanceImpl> getSimilarAssetsInstances(AssetInstanceImpl ai, int pageNum, int iteamPerPage) {
        return this.getAllAssetsInstances(1,4,new SearchQueryImpl(new ArrayList<>(Collections.singleton(ai.getBook().getLanguage())),new ArrayList<>(Collections.singleton(ai.getPhysicalCondition().toString())),ai.getBook().getName(),1,5)).getBooks().stream().filter(assetInstance -> assetInstance.getId() != ai.getId()).collect(Collectors.toList());
    }
}
