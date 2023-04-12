package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
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
    private AssetInstanceDao assetInstanceDao;

    @Override
    public HashMap<String, String> getAssetInstanceDisplay(String id) {
        if (! id.equals("1234")) {
            return null;
        }
        // Mock. Debe conectarse con la capa de persistencia.
        // TODO. Una vez la capa de persistencia este implementada, devolverla
      //  AssetInstance assetInstance = new AssetInstanceImpl();
        // HashMap<String, String> info = assetInstance.display(); TODO
        HashMap<String, String> info = new HashMap<>();
        info.put("id", id);
        info.put("name", "Mistborn");
        info.put("type", "book");
        info.put("isbn", "9780765311788");
        info.put("author", "Brandon Sanderson");
        info.put("language", "English");
        info.put("physicalCondition", "new");
        info.put("locationPC", "1425");
        info.put("location", "CABA");
        info.put("province", "CABA");
        info.put("country", "Argentina");

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
