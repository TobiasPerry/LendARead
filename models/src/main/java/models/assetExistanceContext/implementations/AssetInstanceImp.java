package models.assetExistanceContext.implementations;

import models.assetExistanceContext.interfaces.AssetInstance;
import models.implementations.UserImpl;
import models.userContext.implementations.LocationImpl;

public class AssetInstanceImp implements AssetInstance {
    private BookImp bookImp;

    private PhysicalCondition physicalCondition;

    private UserImpl userReference;

    private LocationImpl location;
}
