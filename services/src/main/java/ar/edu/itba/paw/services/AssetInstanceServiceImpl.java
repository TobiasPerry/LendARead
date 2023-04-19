package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
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

        final int itemsPerPage = 20;

        Optional<Page> optionalPage = assetInstanceDao.getAllAssetInstances(pageNum, itemsPerPage);

        Page page;

        if(optionalPage.isPresent()) {
            page = optionalPage.get();
            return page;
        }
        return new PageImpl(new ArrayList<>(), 1, 1);

    }

}
