package ar.edu.itba.paw.models.assetExistanceContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;

public interface AssetInstance {
    Book getBook();

    User getOwner();

    Location getLocation();

    PhysicalCondition getPhysicalCondition();



}
