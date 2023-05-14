package ar.edu.itba.paw.services;
import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {

    private final AssetDao assetDao;

    private final AssetInstanceDao assetInstanceDao;

    @Autowired
    public AssetInstanceServiceImpl(final AssetDao assetDao, final AssetInstanceDao assetInstanceDao) {
        this.assetDao = assetDao;
        this.assetInstanceDao = assetInstanceDao;
    }

    @Transactional(readOnly = true)
    @Override
    public AssetInstance getAssetInstance(final int id) throws AssetInstanceNotFoundException {
        Optional<AssetInstance> assetInstanceOpt = this.assetInstanceDao.getAssetInstance(id);
        if (!assetInstanceOpt.isPresent())
            throw new AssetInstanceNotFoundException("assetInstance not found");


        AssetInstance assetInstance = assetInstanceOpt.get();
        return assetInstance;
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllAssetsInstances(final int pageNum,final int itemsPerPage){
        return getAllAssetsInstances(pageNum, itemsPerPage, new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), ""));
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllAssetsInstances(final int pageNum,final int itemsPerPage, SearchQuery searchQuery){

        if(searchQuery == null)
            searchQuery = new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "");


        Optional<Page> optionalPage = assetInstanceDao.getAllAssetInstances(pageNum, itemsPerPage, searchQuery);

        Page page;

        if(optionalPage.isPresent()) {
            page = optionalPage.get();
            return page;
        }
        return new PageImpl(new ArrayList<>(), 1, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AssetInstance> getAllAssetsInstances() {
        int pageNumber = 1;
        int itemPerPage = 15;
        Page allAssetInstances = getAllAssetsInstances(pageNumber,itemPerPage);

        List<AssetInstance> combinedBooks = new ArrayList<>(allAssetInstances.getBooks());
        while (allAssetInstances.getCurrentPage() < allAssetInstances.getTotalPages()) {
            pageNumber++;
            allAssetInstances = getAllAssetsInstances(pageNumber,itemPerPage);
            combinedBooks.addAll(allAssetInstances.getBooks());
        }

       return combinedBooks;
    }
    @Transactional
    @Override
    public void removeAssetInstance(final int id) throws AssetInstanceNotFoundException {
         boolean wasRemoved = assetDao.deleteAsset(id);
         if(!wasRemoved)
             throw new AssetInstanceNotFoundException("Asset instance no found");
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isOwner(final int id,final String email) throws AssetInstanceNotFoundException {
        AssetInstance assetInstance = getAssetInstance(id);
        return assetInstance.getOwner().getEmail().equals(email);
    }

}
