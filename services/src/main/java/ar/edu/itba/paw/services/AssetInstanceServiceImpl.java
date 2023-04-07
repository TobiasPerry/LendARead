package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import models.assetExistanceContext.implementations.AssetInstanceImpl;
import models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {

    @Override
    public HashMap<String, String> getAssetInstanceDisplay(String id) {
        if (! id.equals("1234")) {
            return null;
        }
        // Mock. Debe conectarse con la capa de persistencia.
        // TODO. Una vez la capa de persistencia este implementada, devolverla
        AssetInstance assetInstance = new AssetInstanceImpl();
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
}
