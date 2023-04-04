package models.assetExistanceContext.implementations;

import models.assetExistanceContext.interfaces.AssetInstance;
import models.userContext.implementations.LocationImpl;
import models.userContext.implementations.UserImpl;

public class AssetInstanceImp implements AssetInstance {
    private BookImp bookImp;

    private PhysicalCondition physicalCondition;

    private UserImpl userReference;

    private LocationImpl location;
}
