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

    @Autowired
    AssetDao assetDao;

    @Autowired
    AssetInstanceDao assetInstanceDao;

    @Override
    public HashMap<String, String> getAssetInstanceDisplay(int id) {
        Optional<AssetInstance> assetInstanceOpt = this.assetInstanceDao.getAssetInstance(id);
        if (! assetInstanceOpt.isPresent()) {
            return null;
        }

        AssetInstance assetInstance = assetInstanceOpt.get();
        Book book = assetInstance.getBook();
        Location loc = assetInstance.getLocation();
        HashMap<String, String> info = new HashMap<>();
        info.put("id", Integer.toString(id));
        info.put("name", book.getName());
        info.put("type", book.getType());
        info.put("isbn", book.getIsbn());
        info.put("author", book.getAuthor());
        info.put("language", book.getLanguage());
        info.put("physicalCondition", assetInstance.getPhysicalCondition().toString());
        info.put("locationPC", loc.getZipcode());
        info.put("location", loc.getLocality());
        info.put("province", loc.getProvince());
        info.put("country", loc.getCountry());
        return info;
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
