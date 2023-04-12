package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

import java.util.HashMap;
import java.util.List;

public interface AssetInstanceService {
    HashMap<String, String> getAssetInstanceDisplay(int id);

    public List<AssetInstance> getAllAssetsInstances();
}
