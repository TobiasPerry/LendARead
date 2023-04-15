package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
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

    public List<AssetInstance> getAllAssetsInstances(){
        //List<AssetInstance> books = new ArrayList<>();

        Optional<List<AssetInstance>> optionalAssetInstances = assetInstanceDao.getAllAssetInstances();

        List<AssetInstance> assetInstances;

        if(optionalAssetInstances.isPresent()) {
            assetInstances = optionalAssetInstances.get();
            return assetInstances;
        }
        return new ArrayList<>();
//        books.add(new AssetInstanceImpl(0, new BookImpl("12324", "Phill Knight", "Shoe Dog", "English"), PhysicalCondition.ASNEW, new UserImpl("a@a.com", "ippo", "hola"), new LocationImpl("1234", "vil", "BS", "AR")));
//        books.add(new AssetInstanceImpl(0, new BookImpl("12324", "Knight", "Biblia", "English"), PhysicalCondition.ASNEW, new UserImpl("a@a.com", "ippo", "hola"), new LocationImpl("1234", "vil", "BS", "AR")));
//        books.add(new AssetInstanceImpl(0, new BookImpl("12324", "Phill ", "og", "English"), PhysicalCondition.ASNEW, new UserImpl("a@a.com", "ippo", "hola"), new LocationImpl("1234", "vil", "BS", "AR")));
//        books.add(new AssetInstanceImpl(0, new BookImpl("12324", "Evans", "DDD", "English"), PhysicalCondition.ASNEW, new UserImpl("a@a.com", "ippo", "hola"), new LocationImpl("1234", "vil", "BS", "AR")));

    }

}
