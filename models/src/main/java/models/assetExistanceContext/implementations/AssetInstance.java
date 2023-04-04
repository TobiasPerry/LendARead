package models.assetExistanceContext.implementations;

import models.userContext.implementations.UserImpl;
import models.userContext.implementations.LocationImpl;

public class AssetInstance {
    private Book book;

    private PhysicalCondition physicalCondition;

    private UserImpl userReference;

    private LocationImpl location;
}
