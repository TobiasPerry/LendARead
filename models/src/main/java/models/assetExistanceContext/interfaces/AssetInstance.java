package models.assetExistanceContext.interfaces;

import models.assetExistanceContext.implementations.PhysicalCondition;
import models.userContext.interfaces.Location;
import models.userContext.interfaces.User;

public interface AssetInstance {
    Book getBook();

    User getOwner();

    Location getLocation();

    PhysicalCondition getPhysicalCondition();



}
