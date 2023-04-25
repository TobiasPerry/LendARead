package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {

    private final AssetDao assetDao;

    private final AssetInstanceDao assetInstanceDao;

    @Autowired
    public AssetInstanceServiceImpl(AssetDao assetDao, AssetInstanceDao assetInstanceDao) {
        this.assetDao = assetDao;
        this.assetInstanceDao = assetInstanceDao;
    }

    @Override
    public Optional<AssetInstance> getAssetInstance(int id) {
        Optional<AssetInstance> assetInstanceOpt = this.assetInstanceDao.getAssetInstance(id);
        if (!assetInstanceOpt.isPresent()) {
            return null;
        }

        AssetInstance assetInstance = assetInstanceOpt.get();
        return Optional.of(assetInstance);
    }

    public Page getAllAssetsInstances(int pageNum){

        final int itemsPerPage = 15;


        List<String> authors = new ArrayList<>();
        authors.add("Andre Agassi");
        List<String> lang = new ArrayList<>();
        lang.add("English");
        List<String> phy = new ArrayList<>();
        phy.add("ASNEW");

        Optional<Page> optionalPage = assetInstanceDao.getAllAssetInstances(pageNum, itemsPerPage, authors, lang, phy);

        Page page;

        if(optionalPage.isPresent()) {
            page = optionalPage.get();
            return page;
        }
        return new PageImpl(new ArrayList<>(), 1, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    }

    @Override
    public Page getAllAssetsInstances(int pageNum, SearchQuery searchQuery) {
        return getAllAssetsInstances(pageNum);
    }

    @Override
    public boolean removeAssetInstance(int id) {
        return assetDao.deleteAsset(id);
    }

}
