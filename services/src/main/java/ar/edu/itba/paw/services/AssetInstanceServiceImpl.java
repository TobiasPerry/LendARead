package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    private final AssetDao assetDao;

    private final AssetInstanceDao assetInstanceDao;

    @Autowired
    public AssetInstanceServiceImpl(final AssetDao assetDao, final AssetInstanceDao assetInstanceDao) {
        this.assetDao = assetDao;
        this.assetInstanceDao = assetInstanceDao;
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
        return getAllAssetsInstances(pageNum, itemsPerPage, new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), ""));
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllAssetsInstances(final int pageNum, final int itemsPerPage, SearchQuery searchQuery) {

        if (pageNum < 0 || itemsPerPage <= 0)
            return new PageImpl(new ArrayList<>(), 1, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        if (searchQuery == null)
            searchQuery = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "");


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
        boolean wasRemoved = assetInstanceDao.changeStatus(id, AssetState.DELETED);
        if (!wasRemoved) {
            LOGGER.error("Failed to remove the asset instance");
            throw new AssetInstanceNotFoundException("Asset instance no found");
        }
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

}
