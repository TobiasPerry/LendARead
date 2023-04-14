package ar.edu.itba.paw.models.assetExistanceContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;

public interface AssetInstance {
    Book getBook();

    User getOwner();

    Location getLocation();

    AssetState getAssetState();

    int getId();

    PhysicalCondition getPhysicalCondition();

    int getImageId();

}
