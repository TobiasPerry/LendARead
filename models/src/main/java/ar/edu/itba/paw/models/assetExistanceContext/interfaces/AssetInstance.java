package ar.edu.itba.paw.models.assetExistanceContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

public interface AssetInstance {

    BookImpl getBook();

    UserImpl getOwner();

    LocationImpl getLocation();

    AssetState getAssetState();

    int getMaxDays();

    int getId();

    PhysicalCondition getPhysicalCondition();

    int getImage();

    boolean getIsBorrowedInstance();
}
