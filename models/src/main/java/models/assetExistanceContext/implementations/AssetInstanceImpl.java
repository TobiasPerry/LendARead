package models.assetExistanceContext.implementations;

import models.assetExistanceContext.interfaces.AssetInstance;
import models.userContext.implementations.LocationImpl;
import models.userContext.implementations.UserImpl;

public class AssetInstanceImpl implements AssetInstance {
    private BookImpl bookImpl;

    private PhysicalCondition physicalCondition;

    private UserImpl userReference;

    private LocationImpl location;

    public AssetInstanceImpl(BookImpl bookImpl, PhysicalCondition physicalCondition, UserImpl userReference, LocationImpl location) {
        this.bookImpl = bookImpl;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
    }
}
