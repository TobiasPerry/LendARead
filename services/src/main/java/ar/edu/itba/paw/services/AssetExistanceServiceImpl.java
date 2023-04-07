package ar.edu.itba.paw.services;

import interfaces.AssetExistanceService;
import models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.stereotype.Service;

@Service
final public class AssetExistanceServiceImpl implements AssetExistanceService {


    @Override
    public boolean addAssetInstance(AssetInstance assetInstance) {

        System.out.println(assetInstance.toString());
        return true;
    }
}
