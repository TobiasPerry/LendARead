package ar.edu.itba.paw.models.assetExistanceContext.factories;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

public class AssetInstanceFactory {
    public static AssetInstanceImpl createAssetInstance(int id, BookImpl book, PhysicalCondition physicalCondition, UserImpl user, LocationImpl location, ImageImpl photoId, AssetState state, int maxDaysLending) {
        return new AssetInstanceImpl(id,book, physicalCondition, user, location,photoId,state, maxDaysLending);
    }
    public static AssetInstanceImpl createAssetInstance( BookImpl book, PhysicalCondition physicalCondition, UserImpl user, LocationImpl location, ImageImpl photoId, AssetState state, int maxDaysLending) {
        return new AssetInstanceImpl(book, physicalCondition, user, location,photoId,state, maxDaysLending);
    }
}
